package com.lch.lottery.topic;

import com.lchli.pinedrecyclerlistview.library.ListSectionData;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicSection extends ListSectionData {

    public String tag;

    public TopicSection(int sectionViewType,String tag) {
        super(sectionViewType);
        this.tag=tag;
    }
}
