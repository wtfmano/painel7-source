package androidx.core.app;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import androidx.versionedparcelable.VersionedParcelable;

/* loaded from: classes.dex */
public final class RemoteActionCompat implements VersionedParcelable {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PendingIntent mActionIntent;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CharSequence mContentDescription;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean mEnabled;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public IconCompat mIcon;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean mShouldShowIcon;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CharSequence mTitle;

    public RemoteActionCompat(@NonNull IconCompat icon, @NonNull CharSequence title, @NonNull CharSequence contentDescription, @NonNull PendingIntent intent) {
        this.mIcon = (IconCompat) Preconditions.checkNotNull(icon);
        this.mTitle = (CharSequence) Preconditions.checkNotNull(title);
        this.mContentDescription = (CharSequence) Preconditions.checkNotNull(contentDescription);
        this.mActionIntent = (PendingIntent) Preconditions.checkNotNull(intent);
        this.mEnabled = true;
        this.mShouldShowIcon = true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public RemoteActionCompat() {
    }

    public RemoteActionCompat(@NonNull RemoteActionCompat other) {
        Preconditions.checkNotNull(other);
        this.mIcon = other.mIcon;
        this.mTitle = other.mTitle;
        this.mContentDescription = other.mContentDescription;
        this.mActionIntent = other.mActionIntent;
        this.mEnabled = other.mEnabled;
        this.mShouldShowIcon = other.mShouldShowIcon;
    }

    @NonNull
    @RequiresApi(26)
    public static RemoteActionCompat createFromRemoteAction(@NonNull RemoteAction remoteAction) {
        Preconditions.checkNotNull(remoteAction);
        RemoteActionCompat action = new RemoteActionCompat(IconCompat.createFromIcon(remoteAction.getIcon()), remoteAction.getTitle(), remoteAction.getContentDescription(), remoteAction.getActionIntent());
        action.setEnabled(remoteAction.isEnabled());
        if (Build.VERSION.SDK_INT >= 28) {
            action.setShouldShowIcon(remoteAction.shouldShowIcon());
        }
        return action;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setShouldShowIcon(boolean shouldShowIcon) {
        this.mShouldShowIcon = shouldShowIcon;
    }

    public boolean shouldShowIcon() {
        return this.mShouldShowIcon;
    }

    @NonNull
    public IconCompat getIcon() {
        return this.mIcon;
    }

    @NonNull
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @NonNull
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @NonNull
    public PendingIntent getActionIntent() {
        return this.mActionIntent;
    }

    @NonNull
    @RequiresApi(26)
    public RemoteAction toRemoteAction() {
        RemoteAction action = new RemoteAction(this.mIcon.toIcon(), this.mTitle, this.mContentDescription, this.mActionIntent);
        action.setEnabled(isEnabled());
        if (Build.VERSION.SDK_INT >= 28) {
            action.setShouldShowIcon(shouldShowIcon());
        }
        return action;
    }
}
