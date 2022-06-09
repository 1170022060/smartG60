package com.pingok.vocational.domain.release.vo;

import com.pingok.vocational.domain.release.TblReleasePreset;
import lombok.Data;

import java.util.List;

@Data
public class ReleasePresetEnum {
    private List<TblReleasePreset> releasePresets;
    private List<Long> deviceIds;
}
