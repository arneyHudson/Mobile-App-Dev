package edu.msoe.demastri.lab5transcriptmanager.debug;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class DatePickerFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private DatePickerFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private DatePickerFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static DatePickerFragmentArgs fromBundle(@NonNull Bundle bundle) {
    DatePickerFragmentArgs __result = new DatePickerFragmentArgs();
    bundle.setClassLoader(DatePickerFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("startDate")) {
      Date startDate;
      if (Parcelable.class.isAssignableFrom(Date.class) || Serializable.class.isAssignableFrom(Date.class)) {
        startDate = (Date) bundle.get("startDate");
      } else {
        throw new UnsupportedOperationException(Date.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (startDate == null) {
        throw new IllegalArgumentException("Argument \"startDate\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("startDate", startDate);
    } else {
      throw new IllegalArgumentException("Required argument \"startDate\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static DatePickerFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    DatePickerFragmentArgs __result = new DatePickerFragmentArgs();
    if (savedStateHandle.contains("startDate")) {
      Date startDate;
      startDate = savedStateHandle.get("startDate");
      if (startDate == null) {
        throw new IllegalArgumentException("Argument \"startDate\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("startDate", startDate);
    } else {
      throw new IllegalArgumentException("Required argument \"startDate\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Date getStartDate() {
    return (Date) arguments.get("startDate");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("startDate")) {
      Date startDate = (Date) arguments.get("startDate");
      if (Parcelable.class.isAssignableFrom(Date.class) || startDate == null) {
        __result.putParcelable("startDate", Parcelable.class.cast(startDate));
      } else if (Serializable.class.isAssignableFrom(Date.class)) {
        __result.putSerializable("startDate", Serializable.class.cast(startDate));
      } else {
        throw new UnsupportedOperationException(Date.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("startDate")) {
      Date startDate = (Date) arguments.get("startDate");
      if (Parcelable.class.isAssignableFrom(Date.class) || startDate == null) {
        __result.set("startDate", Parcelable.class.cast(startDate));
      } else if (Serializable.class.isAssignableFrom(Date.class)) {
        __result.set("startDate", Serializable.class.cast(startDate));
      } else {
        throw new UnsupportedOperationException(Date.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
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
    DatePickerFragmentArgs that = (DatePickerFragmentArgs) object;
    if (arguments.containsKey("startDate") != that.arguments.containsKey("startDate")) {
      return false;
    }
    if (getStartDate() != null ? !getStartDate().equals(that.getStartDate()) : that.getStartDate() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "DatePickerFragmentArgs{"
        + "startDate=" + getStartDate()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull DatePickerFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull Date startDate) {
      if (startDate == null) {
        throw new IllegalArgumentException("Argument \"startDate\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("startDate", startDate);
    }

    @NonNull
    public DatePickerFragmentArgs build() {
      DatePickerFragmentArgs result = new DatePickerFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setStartDate(@NonNull Date startDate) {
      if (startDate == null) {
        throw new IllegalArgumentException("Argument \"startDate\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("startDate", startDate);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public Date getStartDate() {
      return (Date) arguments.get("startDate");
    }
  }
}
