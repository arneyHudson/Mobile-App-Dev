package edu.msoe.demastri.lab5transcriptmanager.debug;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import edu.msoe.demastri.lab5transcriptmanager.R;

public class TranscriptFragmentDirections {
  private TranscriptFragmentDirections() {
  }

  @NonNull
  public static ShowClassDetail showClassDetail(@NonNull UUID classID) {
    return new ShowClassDetail(classID);
  }

  public static class ShowClassDetail implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ShowClassDetail(@NonNull UUID classID) {
      if (classID == null) {
        throw new IllegalArgumentException("Argument \"classID\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("classID", classID);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ShowClassDetail setClassID(@NonNull UUID classID) {
      if (classID == null) {
        throw new IllegalArgumentException("Argument \"classID\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("classID", classID);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.show_class_detail;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public UUID getClassID() {
      return (UUID) arguments.get("classID");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ShowClassDetail that = (ShowClassDetail) object;
      if (arguments.containsKey("classID") != that.arguments.containsKey("classID")) {
        return false;
      }
      if (getClassID() != null ? !getClassID().equals(that.getClassID()) : that.getClassID() != null) {
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
      result = 31 * result + (getClassID() != null ? getClassID().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ShowClassDetail(actionId=" + getActionId() + "){"
          + "classID=" + getClassID()
          + "}";
    }
  }
}
