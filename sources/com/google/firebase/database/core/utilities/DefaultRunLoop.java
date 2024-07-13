package com.google.firebase.database.core.utilities;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.RunLoop;
import com.google.firebase.database.core.ThreadInitializer;
import java.lang.Thread;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public abstract class DefaultRunLoop implements RunLoop {
    private ScheduledThreadPoolExecutor executor;

    public abstract void handleException(Throwable th);

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* loaded from: classes.dex */
    public class FirebaseThreadFactory implements ThreadFactory {
        private FirebaseThreadFactory() {
            DefaultRunLoop.this = r1;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable r) {
            Thread thread = DefaultRunLoop.this.getThreadFactory().newThread(r);
            ThreadInitializer initializer = DefaultRunLoop.this.getThreadInitializer();
            initializer.setName(thread, "FirebaseDatabaseWorker");
            initializer.setDaemon(thread, true);
            initializer.setUncaughtExceptionHandler(thread, new Thread.UncaughtExceptionHandler() { // from class: com.google.firebase.database.core.utilities.DefaultRunLoop.FirebaseThreadFactory.1
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread t, Throwable e) {
                    DefaultRunLoop.this.handleException(e);
                }
            });
            return thread;
        }
    }

    protected ThreadFactory getThreadFactory() {
        return Executors.defaultThreadFactory();
    }

    protected ThreadInitializer getThreadInitializer() {
        return ThreadInitializer.defaultInstance;
    }

    public DefaultRunLoop() {
        ThreadFactory threadFactory = new FirebaseThreadFactory();
        this.executor = new ScheduledThreadPoolExecutor(1, threadFactory) { // from class: com.google.firebase.database.core.utilities.DefaultRunLoop.1
            @Override // java.util.concurrent.ThreadPoolExecutor
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (t == null && (r instanceof Future)) {
                    Future<?> future = (Future) r;
                    try {
                        if (future.isDone()) {
                            future.get();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (CancellationException e2) {
                    } catch (ExecutionException ee) {
                        t = ee.getCause();
                    }
                }
                if (t != null) {
                    DefaultRunLoop.this.handleException(t);
                }
            }
        };
        this.executor.setKeepAliveTime(3L, TimeUnit.SECONDS);
    }

    public ScheduledExecutorService getExecutorService() {
        return this.executor;
    }

    @Override // com.google.firebase.database.core.RunLoop
    public void scheduleNow(Runnable runnable) {
        this.executor.execute(runnable);
    }

    @Override // com.google.firebase.database.core.RunLoop
    public ScheduledFuture schedule(Runnable runnable, long milliseconds) {
        return this.executor.schedule(runnable, milliseconds, TimeUnit.MILLISECONDS);
    }

    @Override // com.google.firebase.database.core.RunLoop
    public void shutdown() {
        this.executor.setCorePoolSize(0);
    }

    @Override // com.google.firebase.database.core.RunLoop
    public void restart() {
        this.executor.setCorePoolSize(1);
    }

    public static String messageForException(Throwable t) {
        if (t instanceof OutOfMemoryError) {
            return "Firebase Database encountered an OutOfMemoryError. You may need to reduce the amount of data you are syncing to the client (e.g. by using queries or syncing a deeper path). See https://firebase.google.com/docs/database/ios/structure-data#best_practices_for_data_structure and https://firebase.google.com/docs/database/android/retrieve-data#filtering_data";
        }
        if (t instanceof DatabaseException) {
            return "";
        }
        return "Uncaught exception in Firebase Database runloop (" + FirebaseDatabase.getSdkVersion() + "). Please report to firebase-database-client@google.com";
    }
}
