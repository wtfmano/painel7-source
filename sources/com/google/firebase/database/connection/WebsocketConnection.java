package com.google.firebase.database.connection;

import com.google.firebase.database.connection.util.StringListReader;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.tubesock.WebSocket;
import com.google.firebase.database.tubesock.WebSocketEventHandler;
import com.google.firebase.database.tubesock.WebSocketException;
import com.google.firebase.database.tubesock.WebSocketMessage;
import com.google.firebase.database.util.JsonMapper;
import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.http.protocol.HTTP;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class WebsocketConnection {
    private static final long CONNECT_TIMEOUT_MS = 30000;
    private static final long KEEP_ALIVE_TIMEOUT_MS = 45000;
    private static final int MAX_FRAME_SIZE = 16384;
    private static long connectionId = 0;
    private WSClient conn;
    private ScheduledFuture<?> connectTimeout;
    private final ConnectionContext connectionContext;
    private Delegate delegate;
    private final ScheduledExecutorService executorService;
    private StringListReader frameReader;
    private ScheduledFuture<?> keepAlive;
    private final LogWrapper logger;
    private boolean everConnected = false;
    private boolean isClosed = false;
    private long totalFrames = 0;

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public interface Delegate {
        void onDisconnect(boolean z);

        void onMessage(Map<String, Object> map);
    }

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public interface WSClient {
        void close();

        void connect();

        void send(String str);
    }

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public class WSClientTubesock implements WSClient, WebSocketEventHandler {
        private WebSocket ws;

        private WSClientTubesock(WebSocket ws) {
            WebsocketConnection.this = r2;
            this.ws = ws;
            this.ws.setEventHandler(this);
        }

        @Override // com.google.firebase.database.tubesock.WebSocketEventHandler
        public void onOpen() {
            WebsocketConnection.this.executorService.execute(new Runnable() { // from class: com.google.firebase.database.connection.WebsocketConnection.WSClientTubesock.1
                @Override // java.lang.Runnable
                public void run() {
                    WebsocketConnection.this.connectTimeout.cancel(false);
                    WebsocketConnection.this.everConnected = true;
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("websocket opened", new Object[0]);
                    }
                    WebsocketConnection.this.resetKeepAlive();
                }
            });
        }

        @Override // com.google.firebase.database.tubesock.WebSocketEventHandler
        public void onMessage(WebSocketMessage msg) {
            final String str = msg.getText();
            if (WebsocketConnection.this.logger.logsDebug()) {
                WebsocketConnection.this.logger.debug("ws message: " + str, new Object[0]);
            }
            WebsocketConnection.this.executorService.execute(new Runnable() { // from class: com.google.firebase.database.connection.WebsocketConnection.WSClientTubesock.2
                @Override // java.lang.Runnable
                public void run() {
                    WebsocketConnection.this.handleIncomingFrame(str);
                }
            });
        }

        @Override // com.google.firebase.database.tubesock.WebSocketEventHandler
        public void onClose() {
            WebsocketConnection.this.executorService.execute(new Runnable() { // from class: com.google.firebase.database.connection.WebsocketConnection.WSClientTubesock.3
                @Override // java.lang.Runnable
                public void run() {
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("closed", new Object[0]);
                    }
                    WebsocketConnection.this.onClosed();
                }
            });
        }

        @Override // com.google.firebase.database.tubesock.WebSocketEventHandler
        public void onError(final WebSocketException e) {
            WebsocketConnection.this.executorService.execute(new Runnable() { // from class: com.google.firebase.database.connection.WebsocketConnection.WSClientTubesock.4
                @Override // java.lang.Runnable
                public void run() {
                    if (e.getCause() == null || !(e.getCause() instanceof EOFException)) {
                        WebsocketConnection.this.logger.debug("WebSocket error.", e, new Object[0]);
                    } else {
                        WebsocketConnection.this.logger.debug("WebSocket reached EOF.", new Object[0]);
                    }
                    WebsocketConnection.this.onClosed();
                }
            });
        }

        @Override // com.google.firebase.database.tubesock.WebSocketEventHandler
        public void onLogMessage(String msg) {
            if (WebsocketConnection.this.logger.logsDebug()) {
                WebsocketConnection.this.logger.debug("Tubesock: " + msg, new Object[0]);
            }
        }

        @Override // com.google.firebase.database.connection.WebsocketConnection.WSClient
        public void send(String msg) {
            this.ws.send(msg);
        }

        @Override // com.google.firebase.database.connection.WebsocketConnection.WSClient
        public void close() {
            this.ws.close();
        }

        private void shutdown() {
            this.ws.close();
            try {
                this.ws.blockClose();
            } catch (InterruptedException e) {
                WebsocketConnection.this.logger.error("Interrupted while shutting down websocket threads", e);
            }
        }

        @Override // com.google.firebase.database.connection.WebsocketConnection.WSClient
        public void connect() {
            try {
                this.ws.connect();
            } catch (WebSocketException e) {
                if (WebsocketConnection.this.logger.logsDebug()) {
                    WebsocketConnection.this.logger.debug("Error connecting", e, new Object[0]);
                }
                shutdown();
            }
        }
    }

    public WebsocketConnection(ConnectionContext connectionContext, HostInfo hostInfo, String optCachedHost, Delegate delegate, String optLastSessionId) {
        this.connectionContext = connectionContext;
        this.executorService = connectionContext.getExecutorService();
        this.delegate = delegate;
        long connId = connectionId;
        connectionId = 1 + connId;
        this.logger = new LogWrapper(connectionContext.getLogger(), "WebSocket", "ws_" + connId);
        this.conn = createConnection(hostInfo, optCachedHost, optLastSessionId);
    }

    private WSClient createConnection(HostInfo hostInfo, String optCachedHost, String optLastSessionId) {
        String host = optCachedHost != null ? optCachedHost : hostInfo.getHost();
        URI uri = HostInfo.getConnectionUrl(host, hostInfo.isSecure(), hostInfo.getNamespace(), optLastSessionId);
        Map<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put(HTTP.USER_AGENT, this.connectionContext.getUserAgent());
        WebSocket ws = new WebSocket(this.connectionContext, uri, null, extraHeaders);
        WSClientTubesock client = new WSClientTubesock(ws);
        return client;
    }

    public void open() {
        this.conn.connect();
        this.connectTimeout = this.executorService.schedule(new Runnable() { // from class: com.google.firebase.database.connection.WebsocketConnection.1
            @Override // java.lang.Runnable
            public void run() {
                WebsocketConnection.this.closeIfNeverConnected();
            }
        }, CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }

    public void start() {
    }

    public void close() {
        if (this.logger.logsDebug()) {
            this.logger.debug("websocket is being closed", new Object[0]);
        }
        this.isClosed = true;
        this.conn.close();
        if (this.connectTimeout != null) {
            this.connectTimeout.cancel(true);
        }
        if (this.keepAlive != null) {
            this.keepAlive.cancel(true);
        }
    }

    public void send(Map<String, Object> message) {
        resetKeepAlive();
        try {
            String toSend = JsonMapper.serializeJson(message);
            String[] segs = splitIntoFrames(toSend, 16384);
            if (segs.length > 1) {
                this.conn.send("" + segs.length);
            }
            for (String str : segs) {
                this.conn.send(str);
            }
        } catch (IOException e) {
            this.logger.error("Failed to serialize message: " + message.toString(), e);
            shutdown();
        }
    }

    private void appendFrame(String message) {
        this.frameReader.addString(message);
        this.totalFrames--;
        if (this.totalFrames == 0) {
            try {
                this.frameReader.freeze();
                Map<String, Object> decoded = JsonMapper.parseJson(this.frameReader.toString());
                this.frameReader = null;
                if (this.logger.logsDebug()) {
                    this.logger.debug("handleIncomingFrame complete frame: " + decoded, new Object[0]);
                }
                this.delegate.onMessage(decoded);
            } catch (IOException e) {
                this.logger.error("Error parsing frame: " + this.frameReader.toString(), e);
                close();
                shutdown();
            } catch (ClassCastException e2) {
                this.logger.error("Error parsing frame (cast error): " + this.frameReader.toString(), e2);
                close();
                shutdown();
            }
        }
    }

    private void handleNewFrameCount(int numFrames) {
        this.totalFrames = numFrames;
        this.frameReader = new StringListReader();
        if (this.logger.logsDebug()) {
            this.logger.debug("HandleNewFrameCount: " + this.totalFrames, new Object[0]);
        }
    }

    private String extractFrameCount(String message) {
        if (message.length() <= 6) {
            try {
                int frameCount = Integer.parseInt(message);
                if (frameCount > 0) {
                    handleNewFrameCount(frameCount);
                }
                return null;
            } catch (NumberFormatException e) {
            }
        }
        handleNewFrameCount(1);
        return message;
    }

    public void handleIncomingFrame(String message) {
        if (!this.isClosed) {
            resetKeepAlive();
            if (isBuffering()) {
                appendFrame(message);
                return;
            }
            String remaining = extractFrameCount(message);
            if (remaining != null) {
                appendFrame(remaining);
            }
        }
    }

    public void resetKeepAlive() {
        if (!this.isClosed) {
            if (this.keepAlive != null) {
                this.keepAlive.cancel(false);
                if (this.logger.logsDebug()) {
                    this.logger.debug("Reset keepAlive. Remaining: " + this.keepAlive.getDelay(TimeUnit.MILLISECONDS), new Object[0]);
                }
            } else if (this.logger.logsDebug()) {
                this.logger.debug("Reset keepAlive", new Object[0]);
            }
            this.keepAlive = this.executorService.schedule(nop(), KEEP_ALIVE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        }
    }

    private Runnable nop() {
        return new Runnable() { // from class: com.google.firebase.database.connection.WebsocketConnection.2
            @Override // java.lang.Runnable
            public void run() {
                if (WebsocketConnection.this.conn != null) {
                    WebsocketConnection.this.conn.send("0");
                    WebsocketConnection.this.resetKeepAlive();
                }
            }
        };
    }

    private boolean isBuffering() {
        return this.frameReader != null;
    }

    public void onClosed() {
        if (!this.isClosed) {
            if (this.logger.logsDebug()) {
                this.logger.debug("closing itself", new Object[0]);
            }
            shutdown();
        }
        this.conn = null;
        if (this.keepAlive != null) {
            this.keepAlive.cancel(false);
        }
    }

    private void shutdown() {
        this.isClosed = true;
        this.delegate.onDisconnect(this.everConnected);
    }

    public void closeIfNeverConnected() {
        if (!this.everConnected && !this.isClosed) {
            if (this.logger.logsDebug()) {
                this.logger.debug("timed out on connect", new Object[0]);
            }
            this.conn.close();
        }
    }

    private static String[] splitIntoFrames(String src, int maxFrameSize) {
        if (src.length() <= maxFrameSize) {
            return new String[]{src};
        }
        ArrayList<String> segs = new ArrayList<>();
        int i = 0;
        while (i < src.length()) {
            int end = Math.min(i + maxFrameSize, src.length());
            String seg = src.substring(i, end);
            segs.add(seg);
            i += maxFrameSize;
        }
        return (String[]) segs.toArray(new String[segs.size()]);
    }
}
