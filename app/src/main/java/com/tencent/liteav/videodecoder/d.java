package com.tencent.liteav.videodecoder;

import android.util.*;
import java.io.*;

public class d
{
    private boolean a;
    
    public d() {
        this.a = false;
    }
    
    public byte[] a(final byte[] array) throws IOException {
        if (this.a) {
            String string = "";
            for (int i = 0; i < array.length; ++i) {
                String s = Integer.toHexString(array[i] & 0xFF);
                if (s.length() == 1) {
                    s = "0" + s;
                }
                string = string + " " + s;
            }
            Log.d("[H264SPSModifier]", "old SPS:" + string);
        }
        return this.a(new ByteArrayInputStream(array));
    }
    
    public byte[] a(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final c c = new c(inputStream, byteArrayOutputStream);
        c.b(8, "NALU type");
        final int n = (int)c.a(8, "SPS: profile_idc");
        c.b(8, "SPS: constraint_set_0-3_flag and reserved_zero_4bits");
        final int n2 = (int)c.a(8, "SPS: level_idc");
        c.b("SPS: seq_parameter_set_id");
        if (n == 100 || n == 110 || n == 122 || n == 144) {
            if (c.a("SPS: chroma_format_idc") == 3) {
                c.b(1, "SPS: residual_color_transform_flag");
            }
            c.b("SPS: bit_depth_luma_minus8");
            c.b("SPS: bit_depth_chroma_minus8");
            c.b(1, "SPS: qpprime_y_zero_transform_bypass_flag");
            if (c.d("SPS: seq_scaling_matrix_present_lag")) {
                for (int i = 0; i < 8; ++i) {
                    if (c.d("SPS: seqScalingListPresentFlag")) {
                        if (i < 6) {
                            c.c(16);
                        }
                        else {
                            c.c(64);
                        }
                    }
                }
            }
        }
        c.b("SPS: log2_max_frame_num_minus4");
        final int a = c.a("SPS: pic_order_cnt_type");
        if (a == 0) {
            c.b("SPS: log2_max_pic_order_cnt_lsb_minus4");
        }
        else if (a == 1) {
            c.b(1, "SPS: delta_pic_order_always_zero_flag");
            c.b("SPS: offset_for_non_ref_pic");
            c.b("SPS: offset_for_top_to_bottom_field");
            for (int a2 = c.a("SPS: num_ref_frames_in_pic_order_cnt_cycle"), j = 0; j < a2; ++j) {
                c.b("SPS: offsetForRefFrame [" + j + "]");
            }
        }
        final int a3 = c.a("SPS: num_ref_frames");
        if (this.a) {
            Log.d("[H264SPSModifier]", "SPS: num_ref_frames: " + a3);
        }
        c.b(1, "SPS: gaps_in_frame_num_value_allowed_flag");
        c.b("SPS: pic_width_in_mbs_minus1");
        c.b("SPS: pic_height_in_map_units_minus1");
        if (!c.d("SPS: frame_mbs_only_flag")) {
            c.b(1, "SPS: mb_adaptive_frame_field_flag");
        }
        c.b(1, "SPS: direct_8x8_inference_flag");
        if (c.d("SPS: frame_cropping_flag")) {
            c.b("SPS: frame_crop_left_offset");
            c.b("SPS: frame_crop_right_offset");
            c.b("SPS: frame_crop_top_offset");
            c.b("SPS: frame_crop_bottom_offset");
        }
        if (c.e("SPS: vui_parameters_present_flag")) {
            if (this.a) {
                Log.d("[H264SPSModifier]", "vui_parameters_present_flag exist!! modify max_dec_frame_buffering");
            }
            c.a(true, "VUI set 1: ");
            this.b(c);
        }
        else {
            if (this.a) {
                Log.d("[H264SPSModifier]", "vui_parameters_present_flag NOT exist!! add max_dec_frame_buffering");
            }
            c.a(true, "VUI set 1: ");
            c.a(false, "VUI: aspect_ratio_info_present_flag");
            c.a(false, "VUI: overscan_info_present_flag");
            c.a(false, "VUI: video_signal_type_present_flag");
            c.a(false, "VUI: chroma_loc_info_present_flag");
            c.a(false, "VUI: timing_info_present_flag");
            c.a(false, "VUI: nal_hrd_parameters_present_flag");
            c.a(false, "VUI: vcl_hrd_parameters_present_flag");
            c.a(false, "VUI: pic_struct_present_flag");
            final boolean b = true;
            c.a(b, "VUI: bitstream_restriction_flag");
            if (b) {
                c.a(true, "VUI: motion_vectors_over_pic_boundaries_flag");
                c.c(0, "VUI: max_bytes_per_pic_denom");
                c.c(0, "VUI: max_bits_per_mb_denom");
                c.c(10, "VUI: log2_max_mv_length_horizontal");
                c.c(10, "VUI: log2_max_mv_length_vertical");
                c.c(0, "VUI: num_reorder_frames");
                c.c(1, "VUI: max_dec_frame_buffering");
            }
        }
        c.c();
        final byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (this.a) {
            String string = "";
            for (int k = 0; k < byteArray.length; ++k) {
                String s = Integer.toHexString(byteArray[k] & 0xFF);
                if (s.length() == 1) {
                    s = "0" + s;
                }
                string = string + " " + s;
            }
            Log.d("[H264SPSModifier]", "new SPS:" + string);
        }
        return byteArray;
    }
    
    private void a(final c c) throws IOException {
        final int a = c.a("SPS: cpb_cnt_minus1");
        c.b(4, "HRD: bit_rate_scale");
        c.b(4, "HRD: cpb_size_scale");
        for (int i = 0; i <= a; ++i) {
            c.b("HRD: bit_rate_value_minus1");
            c.b("HRD: cpb_size_value_minus1");
            c.b(1, "HRD: cbr_flag");
        }
        c.b(5, "HRD: initial_cpb_removal_delay_length_minus1");
        c.b(5, "HRD: cpb_removal_delay_length_minus1");
        c.b(5, "HRD: dpb_output_delay_length_minus1");
        c.b(5, "HRD: time_offset_length");
    }
    
    private void b(final c c) throws IOException {
        if (c.d("VUI: aspect_ratio_info_present_flag") && (int)c.a(8, "VUI: aspect_ratio") == 255) {
            c.b(16, "VUI: sar_width");
            c.b(16, "VUI: sar_height");
        }
        if (c.d("VUI: overscan_info_present_flag")) {
            c.b(1, "VUI: overscan_appropriate_flag");
        }
        if (c.d("VUI: video_signal_type_present_flag")) {
            c.b(3, "VUI: video_format");
            c.b(1, "VUI: video_full_range_flag");
            if (c.d("VUI: colour_description_present_flag")) {
                c.b(8, "VUI: colour_primaries");
                c.b(8, "VUI: transfer_characteristics");
                c.b(8, "VUI: matrix_coefficients");
            }
        }
        if (c.d("VUI: chroma_loc_info_present_flag")) {
            c.b("VUI chroma_sample_loc_type_top_field");
            c.b("VUI chroma_sample_loc_type_bottom_field");
        }
        if (c.d("VUI: timing_info_present_flag")) {
            c.b(32, "VUI: num_units_in_tick");
            c.b(32, "VUI: time_scale");
            c.b(1, "VUI: fixed_frame_rate_flag");
        }
        final boolean d = c.d("VUI: nal_hrd_parameters_present_flag");
        if (d) {
            this.a(c);
        }
        final boolean d2 = c.d("VUI: vcl_hrd_parameters_present_flag");
        if (d2) {
            this.a(c);
        }
        if (d || d2) {
            c.b(1, "VUI: low_delay_hrd_flag");
        }
        c.b(1, "VUI: pic_struct_present_flag");
        if (c.e("VUI: bitstream_restriction_flag")) {
            if (this.a) {
                Log.d("[H264SPSModifier]", "steve:VUI has bs restriction!!");
            }
            c.a(true, "VUI: set bitstream_restriction_flag");
            c.d("VUI: motion_vectors_over_pic_boundaries_flag");
            c.b("VUI max_bytes_per_pic_denom");
            c.b("VUI max_bits_per_mb_denom");
            c.b("VUI log2_max_mv_length_horizontal");
            c.b("VUI log2_max_mv_length_vertical");
            c.b("VUI num_reorder_frames");
            c.c(1, "VUI: max_dec_frame_buffering");
        }
        else {
            if (this.a) {
                Log.d("[H264SPSModifier]", "steve:VUI has NO bs restriction!!");
            }
            c.a(true, "VUI: set bitstream_restriction_flag");
            c.a(true, "VUI: motion_vectors_over_pic_boundaries_flag");
            c.c(0, "VUI: max_bytes_per_pic_denom");
            c.c(0, "VUI: max_bits_per_mb_denom");
            c.c(10, "VUI: log2_max_mv_length_horizontal");
            c.c(10, "VUI: log2_max_mv_length_vertical");
            c.c(0, "VUI: num_reorder_frames");
            c.c(1, "VUI: max_dec_frame_buffering");
        }
    }
}
