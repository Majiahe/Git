package cn.qnm.modules.system.entity;




import cn.qnm.base.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;

//功能描述:律师信息实体类

@TableName("sys_lowyer")
public class Lowyer extends DataEntity<Lowyer> {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 国籍
     */
    @TableField("nationality")
    private String nationality;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 执业号码
     */
    @TableField("license_number")
    private int licenseNumber;

    /**
     * 执业证
     */
    @TableField("license_icon")
    private String licenseIcon;

    /**
     * 省
     */
    @TableField(value = "province",strategy= FieldStrategy.IGNORED)
    private String province;

    /**
     * 市
     */
    @TableField("city")
    private String city;

    /**
     * 类别
     */
    @TableField("type")
    private String type;

    /**
     * 手机号码
     */
    @TableField("tel")
    private String tel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLiscenseNumber() {
        return licenseNumber;
    }

    public void setLiscenseNumber(int liscenseNumber) {
        this.licenseNumber = liscenseNumber;
    }

    public String getLicenseIcon() {
        return licenseIcon;
    }

    public void setLicenseIcon(String licenseIcon) {
        this.licenseIcon = licenseIcon;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
