package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产地
 * </p>
 *
 * @author chl
 * @since 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "place_id", type = IdType.AUTO)
    private Integer placeId;

    private String placeName;

    private String placeNum;

    private String introduce;

    /**
     * 0:可用  1:不可用
     */
    @TableLogic
    private String isDelete;


}
