
package ren.hankai.persist.util;

/**
 * 分页信息
 *
 * @author hankai
 * @version 1.0
 * @since Jul 11, 2016 1:56:26 PM
 */
public class Pagination {

    private int page;// 页码，从1开始
    private int size;// 页大小

    private Pagination() {
    }

    /**
     * 典型的通过页码和页大小构建分页信息
     *
     * @param page 页码（从1开始）
     * @param size 页大小
     * @return 分页信息
     * @author hankai
     * @since Jul 11, 2016 2:12:31 PM
     */
    public static Pagination pageAndSize( int page, int size ) {
        Pagination p = new Pagination();
        p.setPage( page );
        p.setSize( size );
        return p;
    }

    /**
     * 根据查询结果集的偏移位置和返回结果数构建分页信息
     * 
     * @param offset 结果集偏移位置（从0开始）
     * @param count 返回结果数
     * @return 分页信息
     * @author hankai
     * @since Jul 11, 2016 2:13:21 PM
     */
    public static Pagination offsetAndCount( int offset, int count ) {
        Pagination p = new Pagination();
        if ( count > 0 ) {
            p.setPage( ( offset / count ) + 1 );
        } else {
            p.setPage( 1 );
        }
        p.setSize( count );
        return p;
    }

    public int getPage() {
        return page;
    }

    public void setPage( int page ) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize( int size ) {
        this.size = size;
    }

    public int getOffset() {
        return ( page - 1 ) * size;
    }
}
