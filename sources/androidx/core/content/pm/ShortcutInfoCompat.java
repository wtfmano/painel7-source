package androidx.core.content.pm;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.Person;
import androidx.core.content.LocusIdCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.net.UriCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class ShortcutInfoCompat {
    private static final String EXTRA_LOCUS_ID = "extraLocusId";
    private static final String EXTRA_LONG_LIVED = "extraLongLived";
    private static final String EXTRA_PERSON_ = "extraPerson_";
    private static final String EXTRA_PERSON_COUNT = "extraPersonCount";
    private static final String EXTRA_SLICE_URI = "extraSliceUri";
    ComponentName mActivity;
    Set<String> mCategories;
    Context mContext;
    CharSequence mDisabledMessage;
    int mDisabledReason;
    PersistableBundle mExtras;
    boolean mHasKeyFieldsOnly;
    IconCompat mIcon;
    String mId;
    Intent[] mIntents;
    boolean mIsAlwaysBadged;
    boolean mIsCached;
    boolean mIsDeclaredInManifest;
    boolean mIsDynamic;
    boolean mIsEnabled = true;
    boolean mIsImmutable;
    boolean mIsLongLived;
    boolean mIsPinned;
    CharSequence mLabel;
    long mLastChangedTimestamp;
    @Nullable
    LocusIdCompat mLocusId;
    CharSequence mLongLabel;
    String mPackageName;
    Person[] mPersons;
    int mRank;
    UserHandle mUser;

    ShortcutInfoCompat() {
    }

    @RequiresApi(25)
    public ShortcutInfo toShortcutInfo() {
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(this.mContext, this.mId).setShortLabel(this.mLabel).setIntents(this.mIntents);
        if (this.mIcon != null) {
            builder.setIcon(this.mIcon.toIcon(this.mContext));
        }
        if (!TextUtils.isEmpty(this.mLongLabel)) {
            builder.setLongLabel(this.mLongLabel);
        }
        if (!TextUtils.isEmpty(this.mDisabledMessage)) {
            builder.setDisabledMessage(this.mDisabledMessage);
        }
        if (this.mActivity != null) {
            builder.setActivity(this.mActivity);
        }
        if (this.mCategories != null) {
            builder.setCategories(this.mCategories);
        }
        builder.setRank(this.mRank);
        if (this.mExtras != null) {
            builder.setExtras(this.mExtras);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            if (this.mPersons != null && this.mPersons.length > 0) {
                android.app.Person[] persons = new android.app.Person[this.mPersons.length];
                for (int i = 0; i < persons.length; i++) {
                    persons[i] = this.mPersons[i].toAndroidPerson();
                }
                builder.setPersons(persons);
            }
            if (this.mLocusId != null) {
                builder.setLocusId(this.mLocusId.toLocusId());
            }
            builder.setLongLived(this.mIsLongLived);
        } else {
            builder.setExtras(buildLegacyExtrasBundle());
        }
        return builder.build();
    }

    @RequiresApi(22)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private PersistableBundle buildLegacyExtrasBundle() {
        if (this.mExtras == null) {
            this.mExtras = new PersistableBundle();
        }
        if (this.mPersons != null && this.mPersons.length > 0) {
            this.mExtras.putInt(EXTRA_PERSON_COUNT, this.mPersons.length);
            for (int i = 0; i < this.mPersons.length; i++) {
                this.mExtras.putPersistableBundle(EXTRA_PERSON_ + (i + 1), this.mPersons[i].toPersistableBundle());
            }
        }
        if (this.mLocusId != null) {
            this.mExtras.putString(EXTRA_LOCUS_ID, this.mLocusId.getId());
        }
        this.mExtras.putBoolean(EXTRA_LONG_LIVED, this.mIsLongLived);
        return this.mExtras;
    }

    public Intent addToIntent(Intent outIntent) {
        outIntent.putExtra("android.intent.extra.shortcut.INTENT", this.mIntents[this.mIntents.length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
        if (this.mIcon != null) {
            Drawable badge = null;
            if (this.mIsAlwaysBadged) {
                PackageManager pm = this.mContext.getPackageManager();
                if (this.mActivity != null) {
                    try {
                        badge = pm.getActivityIcon(this.mActivity);
                    } catch (PackageManager.NameNotFoundException e) {
                    }
                }
                if (badge == null) {
                    badge = this.mContext.getApplicationInfo().loadIcon(pm);
                }
            }
            this.mIcon.addToShortcutIntent(outIntent, badge, this.mContext);
        }
        return outIntent;
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    @NonNull
    public String getPackage() {
        return this.mPackageName;
    }

    @Nullable
    public ComponentName getActivity() {
        return this.mActivity;
    }

    @NonNull
    public CharSequence getShortLabel() {
        return this.mLabel;
    }

    @Nullable
    public CharSequence getLongLabel() {
        return this.mLongLabel;
    }

    @Nullable
    public CharSequence getDisabledMessage() {
        return this.mDisabledMessage;
    }

    public int getDisabledReason() {
        return this.mDisabledReason;
    }

    @NonNull
    public Intent getIntent() {
        return this.mIntents[this.mIntents.length - 1];
    }

    @NonNull
    public Intent[] getIntents() {
        return (Intent[]) Arrays.copyOf(this.mIntents, this.mIntents.length);
    }

    @Nullable
    public Set<String> getCategories() {
        return this.mCategories;
    }

    @Nullable
    public LocusIdCompat getLocusId() {
        return this.mLocusId;
    }

    public int getRank() {
        return this.mRank;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public IconCompat getIcon() {
        return this.mIcon;
    }

    @VisibleForTesting
    @Nullable
    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static Person[] getPersonsFromExtra(@NonNull PersistableBundle bundle) {
        if (bundle == null || !bundle.containsKey(EXTRA_PERSON_COUNT)) {
            return null;
        }
        int personsLength = bundle.getInt(EXTRA_PERSON_COUNT);
        Person[] persons = new Person[personsLength];
        for (int i = 0; i < personsLength; i++) {
            persons[i] = Person.fromPersistableBundle(bundle.getPersistableBundle(EXTRA_PERSON_ + (i + 1)));
        }
        return persons;
    }

    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @VisibleForTesting
    static boolean getLongLivedFromExtra(@Nullable PersistableBundle bundle) {
        if (bundle == null || !bundle.containsKey(EXTRA_LONG_LIVED)) {
            return false;
        }
        return bundle.getBoolean(EXTRA_LONG_LIVED);
    }

    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static List<ShortcutInfoCompat> fromShortcuts(@NonNull Context context, @NonNull List<ShortcutInfo> shortcuts) {
        List<ShortcutInfoCompat> results = new ArrayList<>(shortcuts.size());
        for (ShortcutInfo s : shortcuts) {
            results.add(new Builder(context, s).build());
        }
        return results;
    }

    @Nullable
    public PersistableBundle getExtras() {
        return this.mExtras;
    }

    @Nullable
    public UserHandle getUserHandle() {
        return this.mUser;
    }

    public long getLastChangedTimestamp() {
        return this.mLastChangedTimestamp;
    }

    public boolean isCached() {
        return this.mIsCached;
    }

    public boolean isDynamic() {
        return this.mIsDynamic;
    }

    public boolean isPinned() {
        return this.mIsPinned;
    }

    public boolean isDeclaredInManifest() {
        return this.mIsDeclaredInManifest;
    }

    public boolean isImmutable() {
        return this.mIsImmutable;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean hasKeyFieldsOnly() {
        return this.mHasKeyFieldsOnly;
    }

    @Nullable
    @RequiresApi(25)
    static LocusIdCompat getLocusId(@NonNull ShortcutInfo shortcutInfo) {
        if (Build.VERSION.SDK_INT >= 29) {
            if (shortcutInfo.getLocusId() == null) {
                return null;
            }
            return LocusIdCompat.toLocusIdCompat(shortcutInfo.getLocusId());
        }
        return getLocusIdFromExtra(shortcutInfo.getExtras());
    }

    @Nullable
    @RequiresApi(25)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    private static LocusIdCompat getLocusIdFromExtra(@Nullable PersistableBundle bundle) {
        String locusId;
        if (bundle == null || (locusId = bundle.getString(EXTRA_LOCUS_ID)) == null) {
            return null;
        }
        return new LocusIdCompat(locusId);
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private Map<String, Map<String, List<String>>> mCapabilityBindingParams;
        private Set<String> mCapabilityBindings;
        private final ShortcutInfoCompat mInfo = new ShortcutInfoCompat();
        private boolean mIsConversation;
        private Uri mSliceUri;

        public Builder(@NonNull Context context, @NonNull String id) {
            this.mInfo.mContext = context;
            this.mInfo.mId = id;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull ShortcutInfoCompat shortcutInfo) {
            this.mInfo.mContext = shortcutInfo.mContext;
            this.mInfo.mId = shortcutInfo.mId;
            this.mInfo.mPackageName = shortcutInfo.mPackageName;
            this.mInfo.mIntents = (Intent[]) Arrays.copyOf(shortcutInfo.mIntents, shortcutInfo.mIntents.length);
            this.mInfo.mActivity = shortcutInfo.mActivity;
            this.mInfo.mLabel = shortcutInfo.mLabel;
            this.mInfo.mLongLabel = shortcutInfo.mLongLabel;
            this.mInfo.mDisabledMessage = shortcutInfo.mDisabledMessage;
            this.mInfo.mDisabledReason = shortcutInfo.mDisabledReason;
            this.mInfo.mIcon = shortcutInfo.mIcon;
            this.mInfo.mIsAlwaysBadged = shortcutInfo.mIsAlwaysBadged;
            this.mInfo.mUser = shortcutInfo.mUser;
            this.mInfo.mLastChangedTimestamp = shortcutInfo.mLastChangedTimestamp;
            this.mInfo.mIsCached = shortcutInfo.mIsCached;
            this.mInfo.mIsDynamic = shortcutInfo.mIsDynamic;
            this.mInfo.mIsPinned = shortcutInfo.mIsPinned;
            this.mInfo.mIsDeclaredInManifest = shortcutInfo.mIsDeclaredInManifest;
            this.mInfo.mIsImmutable = shortcutInfo.mIsImmutable;
            this.mInfo.mIsEnabled = shortcutInfo.mIsEnabled;
            this.mInfo.mLocusId = shortcutInfo.mLocusId;
            this.mInfo.mIsLongLived = shortcutInfo.mIsLongLived;
            this.mInfo.mHasKeyFieldsOnly = shortcutInfo.mHasKeyFieldsOnly;
            this.mInfo.mRank = shortcutInfo.mRank;
            if (shortcutInfo.mPersons != null) {
                this.mInfo.mPersons = (Person[]) Arrays.copyOf(shortcutInfo.mPersons, shortcutInfo.mPersons.length);
            }
            if (shortcutInfo.mCategories != null) {
                this.mInfo.mCategories = new HashSet(shortcutInfo.mCategories);
            }
            if (shortcutInfo.mExtras != null) {
                this.mInfo.mExtras = shortcutInfo.mExtras;
            }
        }

        @RequiresApi(25)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Builder(@NonNull Context context, @NonNull ShortcutInfo shortcutInfo) {
            this.mInfo.mContext = context;
            this.mInfo.mId = shortcutInfo.getId();
            this.mInfo.mPackageName = shortcutInfo.getPackage();
            Intent[] intents = shortcutInfo.getIntents();
            this.mInfo.mIntents = (Intent[]) Arrays.copyOf(intents, intents.length);
            this.mInfo.mActivity = shortcutInfo.getActivity();
            this.mInfo.mLabel = shortcutInfo.getShortLabel();
            this.mInfo.mLongLabel = shortcutInfo.getLongLabel();
            this.mInfo.mDisabledMessage = shortcutInfo.getDisabledMessage();
            if (Build.VERSION.SDK_INT >= 28) {
                this.mInfo.mDisabledReason = shortcutInfo.getDisabledReason();
            } else {
                this.mInfo.mDisabledReason = shortcutInfo.isEnabled() ? 0 : 3;
            }
            this.mInfo.mCategories = shortcutInfo.getCategories();
            this.mInfo.mPersons = ShortcutInfoCompat.getPersonsFromExtra(shortcutInfo.getExtras());
            this.mInfo.mUser = shortcutInfo.getUserHandle();
            this.mInfo.mLastChangedTimestamp = shortcutInfo.getLastChangedTimestamp();
            if (Build.VERSION.SDK_INT >= 30) {
                this.mInfo.mIsCached = shortcutInfo.isCached();
            }
            this.mInfo.mIsDynamic = shortcutInfo.isDynamic();
            this.mInfo.mIsPinned = shortcutInfo.isPinned();
            this.mInfo.mIsDeclaredInManifest = shortcutInfo.isDeclaredInManifest();
            this.mInfo.mIsImmutable = shortcutInfo.isImmutable();
            this.mInfo.mIsEnabled = shortcutInfo.isEnabled();
            this.mInfo.mHasKeyFieldsOnly = shortcutInfo.hasKeyFieldsOnly();
            this.mInfo.mLocusId = ShortcutInfoCompat.getLocusId(shortcutInfo);
            this.mInfo.mRank = shortcutInfo.getRank();
            this.mInfo.mExtras = shortcutInfo.getExtras();
        }

        @NonNull
        public Builder setShortLabel(@NonNull CharSequence shortLabel) {
            this.mInfo.mLabel = shortLabel;
            return this;
        }

        @NonNull
        public Builder setLongLabel(@NonNull CharSequence longLabel) {
            this.mInfo.mLongLabel = longLabel;
            return this;
        }

        @NonNull
        public Builder setDisabledMessage(@NonNull CharSequence disabledMessage) {
            this.mInfo.mDisabledMessage = disabledMessage;
            return this;
        }

        @NonNull
        public Builder setIntent(@NonNull Intent intent) {
            return setIntents(new Intent[]{intent});
        }

        @NonNull
        public Builder setIntents(@NonNull Intent[] intents) {
            this.mInfo.mIntents = intents;
            return this;
        }

        @NonNull
        public Builder setIcon(IconCompat icon) {
            this.mInfo.mIcon = icon;
            return this;
        }

        @NonNull
        public Builder setLocusId(@Nullable LocusIdCompat locusId) {
            this.mInfo.mLocusId = locusId;
            return this;
        }

        @NonNull
        public Builder setIsConversation() {
            this.mIsConversation = true;
            return this;
        }

        @NonNull
        public Builder setActivity(@NonNull ComponentName activity) {
            this.mInfo.mActivity = activity;
            return this;
        }

        @NonNull
        public Builder setAlwaysBadged() {
            this.mInfo.mIsAlwaysBadged = true;
            return this;
        }

        @NonNull
        public Builder setPerson(@NonNull Person person) {
            return setPersons(new Person[]{person});
        }

        @NonNull
        public Builder setPersons(@NonNull Person[] persons) {
            this.mInfo.mPersons = persons;
            return this;
        }

        @NonNull
        public Builder setCategories(@NonNull Set<String> categories) {
            this.mInfo.mCategories = categories;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setLongLived() {
            this.mInfo.mIsLongLived = true;
            return this;
        }

        @NonNull
        public Builder setLongLived(boolean longLived) {
            this.mInfo.mIsLongLived = longLived;
            return this;
        }

        @NonNull
        public Builder setRank(int rank) {
            this.mInfo.mRank = rank;
            return this;
        }

        @NonNull
        public Builder setExtras(@NonNull PersistableBundle extras) {
            this.mInfo.mExtras = extras;
            return this;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder addCapabilityBinding(@NonNull String capability) {
            if (this.mCapabilityBindings == null) {
                this.mCapabilityBindings = new HashSet();
            }
            this.mCapabilityBindings.add(capability);
            return this;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder addCapabilityBinding(@NonNull String capability, @NonNull String parameter, @NonNull List<String> parameterValues) {
            addCapabilityBinding(capability);
            if (!parameterValues.isEmpty()) {
                if (this.mCapabilityBindingParams == null) {
                    this.mCapabilityBindingParams = new HashMap();
                }
                if (this.mCapabilityBindingParams.get(capability) == null) {
                    this.mCapabilityBindingParams.put(capability, new HashMap());
                }
                this.mCapabilityBindingParams.get(capability).put(parameter, parameterValues);
            }
            return this;
        }

        @NonNull
        @SuppressLint({"MissingGetterMatchingBuilder"})
        public Builder setSliceUri(@NonNull Uri sliceUri) {
            this.mSliceUri = sliceUri;
            return this;
        }

        @NonNull
        @SuppressLint({"UnsafeNewApiCall"})
        public ShortcutInfoCompat build() {
            if (TextUtils.isEmpty(this.mInfo.mLabel)) {
                throw new IllegalArgumentException("Shortcut must have a non-empty label");
            }
            if (this.mInfo.mIntents == null || this.mInfo.mIntents.length == 0) {
                throw new IllegalArgumentException("Shortcut must have an intent");
            }
            if (this.mIsConversation) {
                if (this.mInfo.mLocusId == null) {
                    this.mInfo.mLocusId = new LocusIdCompat(this.mInfo.mId);
                }
                this.mInfo.mIsLongLived = true;
            }
            if (this.mCapabilityBindings != null) {
                if (this.mInfo.mCategories == null) {
                    this.mInfo.mCategories = new HashSet();
                }
                this.mInfo.mCategories.addAll(this.mCapabilityBindings);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.mCapabilityBindingParams != null) {
                    if (this.mInfo.mExtras == null) {
                        this.mInfo.mExtras = new PersistableBundle();
                    }
                    for (String capability : this.mCapabilityBindingParams.keySet()) {
                        Map<String, List<String>> params = this.mCapabilityBindingParams.get(capability);
                        Set<String> paramNames = params.keySet();
                        this.mInfo.mExtras.putStringArray(capability, (String[]) paramNames.toArray(new String[0]));
                        for (String paramName : params.keySet()) {
                            List<String> value = params.get(paramName);
                            this.mInfo.mExtras.putStringArray(capability + "/" + paramName, value == null ? new String[0] : (String[]) value.toArray(new String[0]));
                        }
                    }
                }
                if (this.mSliceUri != null) {
                    if (this.mInfo.mExtras == null) {
                        this.mInfo.mExtras = new PersistableBundle();
                    }
                    this.mInfo.mExtras.putString(ShortcutInfoCompat.EXTRA_SLICE_URI, UriCompat.toSafeString(this.mSliceUri));
                }
            }
            return this.mInfo;
        }
    }
}
