package com.ambisiss.common.global;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description: 分页数据封装
 * @Data: 2023-4-20 19:27:55
 */
public class GlobalPage<T> {

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总量
     */
    private Long total;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 将PageHelper分页后的list转为分页信息
     * @param list
     * @param <T>
     * @return
     */
    public static <T> GlobalPage<T> restPage(List<T> list){
        GlobalPage<T> globalPage = new GlobalPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        globalPage.setTotal(pageInfo.getTotal());
        globalPage.setPageNum(pageInfo.getPageNum());
        globalPage.setPageSize(pageInfo.getPageSize());
        globalPage.setData(pageInfo.getList());
        globalPage.setTotalPage(pageInfo.getPages());
        return globalPage;
    }

    /**
     * 将SpringData分页后的list转为分页信息
     * @param pageInfo
     * @param <T>
     * @return
     */
    public static <T> GlobalPage<T> restPage(Page<T> pageInfo){
        GlobalPage<T> globalPage = new GlobalPage<>();
        globalPage.setTotal(pageInfo.getTotalElements());
        globalPage.setPageNum(pageInfo.getNumber());
        globalPage.setPageSize(pageInfo.getSize());
        globalPage.setData(pageInfo.getContent());
        globalPage.setTotalPage(pageInfo.getTotalPages());
        return globalPage;
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
