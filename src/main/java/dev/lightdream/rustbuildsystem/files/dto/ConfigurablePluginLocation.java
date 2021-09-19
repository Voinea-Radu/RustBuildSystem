package dev.lightdream.rustbuildsystem.files.dto;

import dev.lightdream.api.files.dto.PluginLocation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class ConfigurablePluginLocation extends PluginLocation {

    public Boolean breakable;

    public ConfigurablePluginLocation(PluginLocation location, boolean breakable) {
        super(location.world, location.x, location.y, location.z, location.rotationX, location.rotationY);
        this.breakable = breakable;
    }

    @Override
    public String toString() {
        return "ConfigurablePluginLocation{" +
                "breakable=" + breakable +
                ", world='" + world + '\'' +
                ", rotationX=" + rotationX +
                ", rotationY=" + rotationY +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            ConfigurablePluginLocation that = (ConfigurablePluginLocation) o;
            return Double.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0 && Double.compare(that.z, this.z) == 0 && Objects.equals(this.world, that.world) && this.breakable.equals(that.breakable);
        }
        return false;

    }

    public int hashCode() {
        return Objects.hash(this.world, this.x, this.y, this.z, this.breakable);
    }
}
