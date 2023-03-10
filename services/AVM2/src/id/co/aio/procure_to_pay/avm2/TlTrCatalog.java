/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TlTrCatalog generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`tl_tr_catalog`")
public class TlTrCatalog implements Serializable {

    private Integer caId;
    private Integer caIdoc;
    private String caTipe;
    private String caNama;
    private String caCategory;
    private String caSpesifikasi;
    private Integer caQty;
    private String caSatuan;
    private Long caHarga;
    private String caLokasi;
    private LocalDateTime caTglProduksi;
    private String caJangkawaktu;
    private Integer caStatus;
    private LocalDateTime caInputdate;
    private String caInputby;

    @Id
    @Column(name = "`ca_id`", nullable = false, scale = 0, precision = 10)
    public Integer getCaId() {
        return this.caId;
    }

    public void setCaId(Integer caId) {
        this.caId = caId;
    }

    @Column(name = "`ca_idoc`", nullable = true, scale = 0, precision = 10)
    public Integer getCaIdoc() {
        return this.caIdoc;
    }

    public void setCaIdoc(Integer caIdoc) {
        this.caIdoc = caIdoc;
    }

    @Column(name = "`ca_tipe`", nullable = true, length = 50)
    public String getCaTipe() {
        return this.caTipe;
    }

    public void setCaTipe(String caTipe) {
        this.caTipe = caTipe;
    }

    @Column(name = "`ca_nama`", nullable = true, length = 2147483647)
    public String getCaNama() {
        return this.caNama;
    }

    public void setCaNama(String caNama) {
        this.caNama = caNama;
    }

    @Column(name = "`ca_category`", nullable = true, length = 2147483647)
    public String getCaCategory() {
        return this.caCategory;
    }

    public void setCaCategory(String caCategory) {
        this.caCategory = caCategory;
    }

    @Column(name = "`ca_spesifikasi`", nullable = true, length = 2147483647)
    public String getCaSpesifikasi() {
        return this.caSpesifikasi;
    }

    public void setCaSpesifikasi(String caSpesifikasi) {
        this.caSpesifikasi = caSpesifikasi;
    }

    @Column(name = "`ca_qty`", nullable = true, scale = 0, precision = 10)
    public Integer getCaQty() {
        return this.caQty;
    }

    public void setCaQty(Integer caQty) {
        this.caQty = caQty;
    }

    @Column(name = "`ca_satuan`", nullable = true, length = 50)
    public String getCaSatuan() {
        return this.caSatuan;
    }

    public void setCaSatuan(String caSatuan) {
        this.caSatuan = caSatuan;
    }

    @Column(name = "`ca_harga`", nullable = true, scale = 0, precision = 19)
    public Long getCaHarga() {
        return this.caHarga;
    }

    public void setCaHarga(Long caHarga) {
        this.caHarga = caHarga;
    }

    @Column(name = "`ca_lokasi`", nullable = true, length = 150)
    public String getCaLokasi() {
        return this.caLokasi;
    }

    public void setCaLokasi(String caLokasi) {
        this.caLokasi = caLokasi;
    }

    @Column(name = "`ca_tgl_produksi`", nullable = true)
    public LocalDateTime getCaTglProduksi() {
        return this.caTglProduksi;
    }

    public void setCaTglProduksi(LocalDateTime caTglProduksi) {
        this.caTglProduksi = caTglProduksi;
    }

    @Column(name = "`ca_jangkawaktu`", nullable = true, length = 50)
    public String getCaJangkawaktu() {
        return this.caJangkawaktu;
    }

    public void setCaJangkawaktu(String caJangkawaktu) {
        this.caJangkawaktu = caJangkawaktu;
    }

    @Column(name = "`ca_status`", nullable = true, scale = 0, precision = 10)
    public Integer getCaStatus() {
        return this.caStatus;
    }

    public void setCaStatus(Integer caStatus) {
        this.caStatus = caStatus;
    }

    @Column(name = "`ca_inputdate`", nullable = true)
    public LocalDateTime getCaInputdate() {
        return this.caInputdate;
    }

    public void setCaInputdate(LocalDateTime caInputdate) {
        this.caInputdate = caInputdate;
    }

    @Column(name = "`ca_inputby`", nullable = true, length = 150)
    public String getCaInputby() {
        return this.caInputby;
    }

    public void setCaInputby(String caInputby) {
        this.caInputby = caInputby;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TlTrCatalog)) return false;
        final TlTrCatalog tlTrCatalog = (TlTrCatalog) o;
        return Objects.equals(getCaId(), tlTrCatalog.getCaId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCaId());
    }
}