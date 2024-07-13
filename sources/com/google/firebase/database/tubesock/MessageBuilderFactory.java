package com.google.firebase.database.tubesock;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public class MessageBuilderFactory {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public interface Builder {
        boolean appendBytes(byte[] bArr);

        WebSocketMessage toMessage();
    }

    MessageBuilderFactory() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public static class BinaryBuilder implements Builder {
        private int pendingByteCount = 0;
        private List<byte[]> pendingBytes = new ArrayList();

        BinaryBuilder() {
        }

        @Override // com.google.firebase.database.tubesock.MessageBuilderFactory.Builder
        public boolean appendBytes(byte[] bytes) {
            this.pendingBytes.add(bytes);
            this.pendingByteCount += bytes.length;
            return true;
        }

        @Override // com.google.firebase.database.tubesock.MessageBuilderFactory.Builder
        public WebSocketMessage toMessage() {
            byte[] payload = new byte[this.pendingByteCount];
            int offset = 0;
            for (int i = 0; i < this.pendingBytes.size(); i++) {
                byte[] segment = this.pendingBytes.get(i);
                System.arraycopy(segment, 0, payload, offset, segment.length);
                offset += segment.length;
            }
            return new WebSocketMessage(payload);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public static class TextBuilder implements Builder {
        private static ThreadLocal<CharsetDecoder> localDecoder = new ThreadLocal<CharsetDecoder>() { // from class: com.google.firebase.database.tubesock.MessageBuilderFactory.TextBuilder.1
            @Override // java.lang.ThreadLocal
            public CharsetDecoder initialValue() {
                Charset utf8 = Charset.forName("UTF8");
                CharsetDecoder decoder = utf8.newDecoder();
                decoder.onMalformedInput(CodingErrorAction.REPORT);
                decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
                return decoder;
            }
        };
        private static ThreadLocal<CharsetEncoder> localEncoder = new ThreadLocal<CharsetEncoder>() { // from class: com.google.firebase.database.tubesock.MessageBuilderFactory.TextBuilder.2
            @Override // java.lang.ThreadLocal
            public CharsetEncoder initialValue() {
                Charset utf8 = Charset.forName("UTF8");
                CharsetEncoder encoder = utf8.newEncoder();
                encoder.onMalformedInput(CodingErrorAction.REPORT);
                encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
                return encoder;
            }
        };
        private StringBuilder builder = new StringBuilder();
        private ByteBuffer carryOver;

        TextBuilder() {
        }

        @Override // com.google.firebase.database.tubesock.MessageBuilderFactory.Builder
        public boolean appendBytes(byte[] bytes) {
            String nextFrame = decodeString(bytes);
            if (nextFrame != null) {
                this.builder.append(nextFrame);
                return true;
            }
            return false;
        }

        @Override // com.google.firebase.database.tubesock.MessageBuilderFactory.Builder
        public WebSocketMessage toMessage() {
            if (this.carryOver != null) {
                return null;
            }
            return new WebSocketMessage(this.builder.toString());
        }

        private String decodeString(byte[] bytes) {
            try {
                ByteBuffer input = ByteBuffer.wrap(bytes);
                CharBuffer buf = localDecoder.get().decode(input);
                return buf.toString();
            } catch (CharacterCodingException e) {
                return null;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x002f, code lost:
            return r7;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x003a, code lost:
            if (r2.remaining() <= 0) goto L20;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x003c, code lost:
            r10.carryOver = r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x003e, code lost:
            r6 = java.nio.CharBuffer.wrap(r4);
            com.google.firebase.database.tubesock.MessageBuilderFactory.TextBuilder.localEncoder.get().encode(r6);
            r4.flip();
            r7 = r4.toString();
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.lang.String decodeStringStreaming(byte[] r11) {
            /*
                r10 = this;
                r7 = 0
                java.nio.ByteBuffer r2 = r10.getBuffer(r11)     // Catch: java.nio.charset.CharacterCodingException -> L6b
                int r8 = r2.remaining()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                float r9 = (float) r8     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.lang.ThreadLocal<java.nio.charset.CharsetDecoder> r8 = com.google.firebase.database.tubesock.MessageBuilderFactory.TextBuilder.localDecoder     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.lang.Object r8 = r8.get()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.nio.charset.CharsetDecoder r8 = (java.nio.charset.CharsetDecoder) r8     // Catch: java.nio.charset.CharacterCodingException -> L6b
                float r8 = r8.averageCharsPerByte()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                float r8 = r8 * r9
                int r0 = (int) r8     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.nio.CharBuffer r4 = java.nio.CharBuffer.allocate(r0)     // Catch: java.nio.charset.CharacterCodingException -> L6b
            L1c:
                java.lang.ThreadLocal<java.nio.charset.CharsetDecoder> r8 = com.google.firebase.database.tubesock.MessageBuilderFactory.TextBuilder.localDecoder     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.lang.Object r8 = r8.get()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.nio.charset.CharsetDecoder r8 = (java.nio.charset.CharsetDecoder) r8     // Catch: java.nio.charset.CharacterCodingException -> L6b
                r9 = 0
                java.nio.charset.CoderResult r5 = r8.decode(r2, r4, r9)     // Catch: java.nio.charset.CharacterCodingException -> L6b
                boolean r8 = r5.isError()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                if (r8 == 0) goto L30
            L2f:
                return r7
            L30:
                boolean r8 = r5.isUnderflow()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                if (r8 == 0) goto L55
                int r8 = r2.remaining()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                if (r8 <= 0) goto L3e
                r10.carryOver = r2     // Catch: java.nio.charset.CharacterCodingException -> L6b
            L3e:
                java.nio.CharBuffer r6 = java.nio.CharBuffer.wrap(r4)     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.lang.ThreadLocal<java.nio.charset.CharsetEncoder> r8 = com.google.firebase.database.tubesock.MessageBuilderFactory.TextBuilder.localEncoder     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.lang.Object r8 = r8.get()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.nio.charset.CharsetEncoder r8 = (java.nio.charset.CharsetEncoder) r8     // Catch: java.nio.charset.CharacterCodingException -> L6b
                r8.encode(r6)     // Catch: java.nio.charset.CharacterCodingException -> L6b
                r4.flip()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                java.lang.String r7 = r4.toString()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                goto L2f
            L55:
                boolean r8 = r5.isOverflow()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                if (r8 == 0) goto L1c
                int r8 = r0 * 2
                int r0 = r8 + 1
                java.nio.CharBuffer r3 = java.nio.CharBuffer.allocate(r0)     // Catch: java.nio.charset.CharacterCodingException -> L6b
                r4.flip()     // Catch: java.nio.charset.CharacterCodingException -> L6b
                r3.put(r4)     // Catch: java.nio.charset.CharacterCodingException -> L6b
                r4 = r3
                goto L1c
            L6b:
                r1 = move-exception
                goto L2f
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.tubesock.MessageBuilderFactory.TextBuilder.decodeStringStreaming(byte[]):java.lang.String");
        }

        private ByteBuffer getBuffer(byte[] bytes) {
            if (this.carryOver != null) {
                ByteBuffer buffer = ByteBuffer.allocate(bytes.length + this.carryOver.remaining());
                buffer.put(this.carryOver);
                this.carryOver = null;
                buffer.put(bytes);
                buffer.flip();
                return buffer;
            }
            return ByteBuffer.wrap(bytes);
        }
    }

    public static Builder builder(byte opcode) {
        return opcode == 2 ? new BinaryBuilder() : new TextBuilder();
    }
}
