package cn.qnm.modules.system.entity;

import cn.qnm.base.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;

//功能描述:角色实体类

@TableName("sys_menu")
public class Menu extends DataEntity<Menu> {
    private static final long serialVersionUID = 1L;

    private String name;

    private String icon;

    /**
     * 链接地址
     */
    @TableField(strategy= FieldStrategy.IGNORED)
    private String href;

    /**
     * 打开方式
     */
    @TableField(strategy= FieldStrategy.IGNORED)
    private String target;

    /**
     * 是否显示
     */
    @TableField(value="is_show",strategy= FieldStrategy.IGNORED)
    private Boolean isShow;

    /**
     * 类型（0表示菜单，1表示按钮，-1表示目录）
     */
    @TableField(value="is_menu",strategy= FieldStrategy.IGNORED)
    private Integer isMenu;

    @TableField("bg_color")
    private String bgColor;

    /**
     * 权限标识
     */
    @TableField(strategy= FieldStrategy.IGNORED)
    private String permission;

    @TableField(exist = false)
    private Integer dataCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }
}
