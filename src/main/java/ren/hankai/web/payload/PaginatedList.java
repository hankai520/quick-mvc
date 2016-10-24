
package ren.hankai.web.payload;

import java.util.List;

/**
 * 用于包装前端表控件数据源
 *
 * @author hankai
 * @version 1.0
 * @since Mar 25, 2016 9:17:29 AM
 */
public class PaginatedList {

  private int total;
  private List<?> rows;

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<?> getRows() {
    return rows;
  }

  public void setRows(List<?> rows) {
    this.rows = rows;
  }
}
