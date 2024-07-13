package com.google.firebase.database.android;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.database.core.AuthTokenProvider;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import java.util.concurrent.ExecutorService;

/* compiled from: com.google.firebase:firebase-database@@19.0.0 */
/* loaded from: classes.dex */
public abstract class AndroidAuthTokenProvider implements AuthTokenProvider {

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* renamed from: com.google.firebase.database.android.AndroidAuthTokenProvider$1 */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements AuthTokenProvider {
        final /* synthetic */ InternalAuthProvider val$authProvider;

        AnonymousClass1(InternalAuthProvider internalAuthProvider) {
            this.val$authProvider = internalAuthProvider;
        }

        @Override // com.google.firebase.database.core.AuthTokenProvider
        public void getToken(boolean forceRefresh, @NonNull AuthTokenProvider.GetTokenCompletionListener listener) {
            Task<GetTokenResult> getTokenResult = this.val$authProvider.getAccessToken(forceRefresh);
            getTokenResult.addOnSuccessListener(AndroidAuthTokenProvider$1$$Lambda$1.lambdaFactory$(listener)).addOnFailureListener(AndroidAuthTokenProvider$1$$Lambda$2.lambdaFactory$(listener));
        }

        public static /* synthetic */ void lambda$getToken$1(AuthTokenProvider.GetTokenCompletionListener listener, Exception e) {
            if (AndroidAuthTokenProvider.isUnauthenticatedUsage(e)) {
                listener.onSuccess(null);
            } else {
                listener.onError(e.getMessage());
            }
        }

        @Override // com.google.firebase.database.core.AuthTokenProvider
        public void addTokenChangeListener(ExecutorService executorService, AuthTokenProvider.TokenChangeListener tokenListener) {
            IdTokenListener idTokenListener = AndroidAuthTokenProvider$1$$Lambda$3.lambdaFactory$(executorService, tokenListener);
            this.val$authProvider.addIdTokenListener(idTokenListener);
        }

        @Override // com.google.firebase.database.core.AuthTokenProvider
        public void removeTokenChangeListener(AuthTokenProvider.TokenChangeListener tokenListener) {
        }
    }

    public static AuthTokenProvider forAuthenticatedAccess(@NonNull InternalAuthProvider authProvider) {
        return new AnonymousClass1(authProvider);
    }

    /* compiled from: com.google.firebase:firebase-database@@19.0.0 */
    /* renamed from: com.google.firebase.database.android.AndroidAuthTokenProvider$2 */
    /* loaded from: classes.dex */
    public class AnonymousClass2 implements AuthTokenProvider {
        AnonymousClass2() {
        }

        @Override // com.google.firebase.database.core.AuthTokenProvider
        public void getToken(boolean forceRefresh, AuthTokenProvider.GetTokenCompletionListener listener) {
            listener.onSuccess(null);
        }

        @Override // com.google.firebase.database.core.AuthTokenProvider
        public void addTokenChangeListener(ExecutorService executorService, AuthTokenProvider.TokenChangeListener listener) {
            executorService.execute(AndroidAuthTokenProvider$2$$Lambda$1.lambdaFactory$(listener));
        }

        @Override // com.google.firebase.database.core.AuthTokenProvider
        public void removeTokenChangeListener(AuthTokenProvider.TokenChangeListener listener) {
        }
    }

    public static AuthTokenProvider forUnauthenticatedAccess() {
        return new AnonymousClass2();
    }

    public static boolean isUnauthenticatedUsage(Exception e) {
        return (e instanceof FirebaseApiNotAvailableException) || (e instanceof FirebaseNoSignedInUserException);
    }
}
