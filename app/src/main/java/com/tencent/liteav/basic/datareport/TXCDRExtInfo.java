package com.tencent.liteav.basic.datareport;

public class TXCDRExtInfo
{
    public String command_id_comment;
    public String url;
    public boolean report_common;
    public boolean report_status;
    public int report_datatime;
    
    public TXCDRExtInfo() {
        this.command_id_comment = "";
        this.url = "";
        this.report_common = true;
        this.report_status = false;
        this.report_datatime = 0;
    }
}
