package cn.redsa.miraisp.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "plugin_book")
public class PluginBook {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plugin_id")
    private String pluginId;

    @Column(name = "enable")
    private Boolean enable = true;
}
