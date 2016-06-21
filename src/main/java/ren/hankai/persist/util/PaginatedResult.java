
package ren.hankai.persist.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页查询返回的结果。
 *
 * @author hankai
 * @version 1.0
 * @since Jul 14, 2015 12:38:04 PM
 */
public class PaginatedResult<T> {

    private int     count;
    private boolean hasMore;
    private List<T> objects;

    /**
     * Override default initializer to initialize member fields.
     */
    public PaginatedResult() {
        this.count = 0;
        this.hasMore = false;
        this.objects = new ArrayList<T>();
    }

    public int getCount() {
        return count;
    }

    public boolean getHasMore() {
        return hasMore;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setCount( int count ) {
        this.count = count;
    }

    public void setHasMore( boolean hasMore ) {
        this.hasMore = hasMore;
    }

    public void setObjects( List<T> objects ) {
        this.objects = objects;
    }
}
