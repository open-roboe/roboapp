package it.halb.roboapp.util;

import androidx.recyclerview.widget.LinearLayoutManager;

public class LinearLayoutManagerWrapper extends LinearLayoutManager {
public LinearLayoutManagerWrapper(android.content.Context context) {
        super(context);
    }

    public LinearLayoutManagerWrapper(android.content.Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerWrapper(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
