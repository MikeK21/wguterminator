package com.example.wguterminator.Entities;


import android.widget.DatePicker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
Class for terms
 */
@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termId;
    private String termName;
    private String termDate;

    public Term(int termId, String termName, String termDate) {
        this.termId = termId;
        this.termName = termName;
        this.termDate = termDate;
    }

    public Term() {
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermDate() {
        return termDate;
    }

    public void setTermDate(String termDate) {
        this.termDate = termDate;
    }
}
