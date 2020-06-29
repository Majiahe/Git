package cn.qnm.modules.system.entity;


import cn.qnm.base.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

//功能描述:订单信息实体类
@TableName("sys_order")
public class Order extends DataEntity<Order> {
    private static final long serialVersionUID = 1L;

    /**
     * 订单类型
     */
    @TableField("type")
    private String type;

    /**
     * 订单金额
     */
    @TableField("cost")
    private int cost;

    /**
     * 订单明细
     */
    @TableField("cost_info")
    private String costInfo;

    /**
     * 订单状态（已支付/未支付）
     */
    @TableField("status")
    private String status;

    /**
     * 评价
     */
    @TableField("record")
    private String record;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCostInfo() {
        return costInfo;
    }

    public void setCostInfo(String costInfo) {
        this.costInfo = costInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
