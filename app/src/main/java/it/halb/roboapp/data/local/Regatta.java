package it.halb.roboapp.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity(tableName = "regatta_table")
public class Regatta {
    /*
        private BigDecimal compassDegrees;
        private BigDecimal startLineLen;
        private BigDecimal breakDistance;
        private BigDecimal courseLength;
        private BigDecimal secondMarkDistance;
         */
    @PrimaryKey
    @NonNull
    private String name;
    private String type;

    private int creationDate;

    public Regatta(@NonNull String name, String type, int creationDate) {
        this.name = name;
        this.type = type;
        this.creationDate = creationDate;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }
}