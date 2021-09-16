package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.files.dto.Position;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class ConfigurablePosition extends Position implements java.io.Serializable {

    public Boolean breakable;

    public ConfigurablePosition(Position position, boolean breakable) {
        super(position.x, position.y, position.z);
        this.breakable = breakable;
    }

    public ConfigurablePosition(Integer x, Integer y, Integer z, boolean breakable) {
        super(x, y, z);
        this.breakable = breakable;
    }

    @Override
    public String toString() {
        return "ConfigurablePosition{" +
                "breakable=" + breakable +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ConfigurablePosition position = (ConfigurablePosition) o;
            return this.x.equals(position.x) && this.y.equals(position.y) && this.z.equals(position.z) && this.breakable == position.breakable;
        }
        return false;

    }

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z, breakable);
    }


}
