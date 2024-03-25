package edu.msoe.demastri.lab5transcriptmanager.debug;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import edu.msoe.demastri.lab5transcriptmanager.R;

public class ClassDetailFragmentDirections {
  private ClassDetailFragmentDirections() {
  }

  @NonNull
  public static SelectDate selectDate(@NonNull Date startDate) {
    return new SelectDate(startDate);
  }

  public static class SelectDate implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private SelectDate(@NonNull Date startDate) {
      if (startDate == null) {
        throw new IllegalArgumentException("Argument \"startDate\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("startDate", startDate);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public SelectDate setStartDate(@NonNull Date startDate) {
      if (startDate == null) {
        throw new IllegalArgumentException("Argument \"startDate\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("startDate", startDate);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.select_date;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Date getStartDate() {
      return (Date) arguments.get("startDate");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      SelectDate that = (SelectDate) object;
      if (arguments.containsKey("startDate") != that.arguments.containsKey("startDate")) {
        return false;
      }
      if (getStartDate() != null ? !getStartDate().equals(that.getStartDate()) : that.getStartDate() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "SelectDate(actionId=" + getActionId() + "){"
          + "startDate=" + getStartDate()
          + "}";
    }
  }
}
