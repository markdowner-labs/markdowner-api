package org.markdowner.api.util;

public interface Viewer {
    interface Public {}
    interface Protected extends Public {}
    interface Private extends Protected {}
}
