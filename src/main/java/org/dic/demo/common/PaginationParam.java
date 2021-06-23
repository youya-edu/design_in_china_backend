package org.dic.demo.common;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PaginationParam {
  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_PAGE_SIZE = 20;
  private static final int MAX_PAGE_SIZE = 50;

  private int page = DEFAULT_PAGE;
  private int pageSize = DEFAULT_PAGE_SIZE;

  public PaginationParam setPage(int page) {
    this.page = page;
    return this;
  }

  public PaginationParam setPageSize(int pageSize) {
    this.pageSize = Math.min(pageSize, MAX_PAGE_SIZE);
    return this;
  }
}
