package androidx.core.provider;

import android.os.Handler;
import android.os.Process;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class RequestExecutor {
    private RequestExecutor() {
    }

    public static <T> void execute(@NonNull Executor executor, @NonNull Callable<T> callable, @NonNull Consumer<T> consumer) {
        Handler calleeHandler = CalleeHandler.create();
        executor.execute(new ReplyRunnable(calleeHandler, callable, consumer));
    }

    public static <T> T submit(@NonNull ExecutorService executor, @NonNull Callable<T> callable, @IntRange(from = 0) int timeoutMillis) throws InterruptedException {
        Future<T> future = executor.submit(callable);
        try {
            return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e2) {
            throw new RuntimeException(e2);
        } catch (TimeoutException e3) {
            throw new InterruptedException("timeout");
        }
    }

    public static ThreadPoolExecutor createDefaultExecutor(@NonNull String threadName, int threadPriority, @IntRange(from = 0) int keepAliveTimeInMillis) {
        ThreadFactory threadFactory = new DefaultThreadFactory(threadName, threadPriority);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, keepAliveTimeInMillis, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), threadFactory);
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    public static Executor createHandlerExecutor(@NonNull Handler handler) {
        return new HandlerExecutor(handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class HandlerExecutor implements Executor {
        private final Handler mHandler;

        HandlerExecutor(@NonNull Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable command) {
            if (!this.mHandler.post((Runnable) Preconditions.checkNotNull(command))) {
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class ReplyRunnable<T> implements Runnable {
        @NonNull
        private Callable<T> mCallable;
        @NonNull
        private Consumer<T> mConsumer;
        @NonNull
        private Handler mHandler;

        ReplyRunnable(@NonNull Handler handler, @NonNull Callable<T> callable, @NonNull Consumer<T> consumer) {
            this.mCallable = callable;
            this.mConsumer = consumer;
            this.mHandler = handler;
        }

        @Override // java.lang.Runnable
        public void run() {
            final T result;
            try {
                T t = this.mCallable.call();
                result = t;
            } catch (Exception e) {
                result = null;
            }
            final Consumer<T> consumer = this.mConsumer;
            this.mHandler.post(new Runnable() { // from class: androidx.core.provider.RequestExecutor.ReplyRunnable.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    consumer.accept(result);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class DefaultThreadFactory implements ThreadFactory {
        private int mPriority;
        private String mThreadName;

        DefaultThreadFactory(@NonNull String threadName, int priority) {
            this.mThreadName = threadName;
            this.mPriority = priority;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new ProcessPriorityThread(runnable, this.mThreadName, this.mPriority);
        }

        /* loaded from: classes.dex */
        private static class ProcessPriorityThread extends Thread {
            private final int mPriority;

            ProcessPriorityThread(Runnable target, String name, int priority) {
                super(target, name);
                this.mPriority = priority;
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Process.setThreadPriority(this.mPriority);
                super.run();
            }
        }
    }
}
