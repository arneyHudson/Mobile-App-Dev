package edu.msoe.demastri.lab5transcriptmanager.debug;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class ClassDetailFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ClassDetailFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private ClassDetailFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ClassDetailFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ClassDetailFragmentArgs __result = new ClassDetailFragmentArgs();
    bundle.setClassLoader(ClassDetailFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("classID")) {
      UUID classID;
      if (Parcelable.class.isAssignableFrom(UUID.class) || Serializable.class.isAssignableFrom(UUID.class)) {
        classID = (UUID) bundle.get("classID");
      } else {
        throw new UnsupportedOperationException(UUID.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (classID == null) {
        throw new IllegalArgumentException("Argument \"classID\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("classID", classID);
    } else {
      throw new IllegalArgumentException("Required argument \"classID\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ClassDetailFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    ClassDetailFragmentArgs __result = new ClassDetailFragmentArgs();
    if (savedStateHandle.contains("classID")) {
      UUID classID;
      classID = savedStateHandle.get("classID");
      if (classID == null) {
        throw new IllegalArgumentException("Argument \"classID\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("classID", classID);
    } else {
      throw new IllegalArgumentException("Required argument \"classID\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public UUID getClassID() {
    return (UUID) arguments.get("classID");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("classID")) {
      UUID classID = (UUID) arguments.get("classID");
      if (Parcelable.class.isAssignableFrom(UUID.class) || classID == null) {
        __result.putParcelable("classID", Parcelable.class.cast(classID));
      } else if (Serializable.class.isAssignableFrom(UUID.class)) {
        __result.putSerializable("classID", Serializable.class.cast(classID));
      } else {
        throw new UnsupportedOperationException(UUID.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("classID")) {
      UUID classID = (UUID) arguments.get("classID");
      if (Parcelable.class.isAssignableFrom(UUID.class) || classID == null) {
        __result.set("classID", Parcelable.class.cast(classID));
      } else if (Serializable.class.isAssignableFrom(UUID.class)) {
        __result.set("classID", Serializable.class.cast(classID));
      } else {
        throw new UnsupportedOperationException(UUID.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    ClassDetailFragmentArgs that = (ClassDetailFragmentArgs) object;
    if (arguments.containsKey("classID") != that.arguments.containsKey("classID")) {
      return false;
    }
    if (getClassID() != null ? !getClassID().equals(that.getClassID()) : that.getClassID() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getClassID() != null ? getClassID().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ClassDetailFragmentArgs{"
        + "classID=" + getClassID()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ClassDetailFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull UUID classID) {
      if (classID == null) {
        throw new IllegalArgumentException("Argument \"classID\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("classID", classID);
    }

    @NonNull
    public ClassDetailFragmentArgs build() {
      ClassDetailFragmentArgs result = new ClassDetailFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setClassID(@NonNull UUID classID) {
      if (classID == null) {
        throw new IllegalArgumentException("Argument \"classID\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("classID", classID);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public UUID getClassID() {
      return (UUID) arguments.get("classID");
    }
  }
}
