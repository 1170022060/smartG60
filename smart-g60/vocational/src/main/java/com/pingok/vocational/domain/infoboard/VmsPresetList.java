package com.pingok.vocational.domain.infoboard;

import com.pingok.vocational.domain.release.TblReleasePreset;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VmsPresetList {

    private String presetInfo;

    private List<TblReleasePreset> presetList;

}
