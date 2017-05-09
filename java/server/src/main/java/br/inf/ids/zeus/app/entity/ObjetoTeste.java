package br.inf.ids.zeus.app.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class ObjetoTeste {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vlong;
	
	@Column(nullable=false)
	private Integer vinteger;
	
	@Column
	private Boolean vbooleantrue;
	
	@Column
	private Boolean vbooleanfalse;
	
	@Column
	private Double vdouble;
	
	@Column
	private Float vfloat;
	
	@Column(precision=35, scale=10)
	private BigDecimal vbigdecimal;
	
	@Column(length=100)
	private String vstring;
	
	@Column(columnDefinition="text")
	private String vmemo;
	
	@Column
	private LocalDate vlocaldate;
	
	public Integer getVinteger() {
		return vinteger;
	}
	public void setVinteger(Integer vinteger) {
		this.vinteger = vinteger;
	}
	public Long getVlong() {
		return vlong;
	}
	public void setVlong(Long vlong) {
		this.vlong = vlong;
	}
	public Boolean getVbooleantrue() {
		return vbooleantrue;
	}
	public void setVbooleantrue(Boolean vbooleantrue) {
		this.vbooleantrue = vbooleantrue;
	}
	public Boolean getVbooleanfalse() {
		return vbooleanfalse;
	}
	public void setVbooleanfalse(Boolean vbooleanfalse) {
		this.vbooleanfalse = vbooleanfalse;
	}
	public Double getVdouble() {
		return vdouble;
	}
	public void setVdouble(Double vdouble) {
		this.vdouble = vdouble;
	}
	public Float getVfloat() {
		return vfloat;
	}
	public void setVfloat(Float vfloat) {
		this.vfloat = vfloat;
	}
	public BigDecimal getVbigdecimal() {
		return vbigdecimal;
	}
	public void setVbigdecimal(BigDecimal vbigdecimal) {
		this.vbigdecimal = vbigdecimal;
	}
	public String getVstring() {
		return vstring;
	}
	public void setVstring(String vstring) {
		this.vstring = vstring;
	}
	public LocalDate getVlocaldate() {
		return vlocaldate;
	}
	public void setVlocaldate(LocalDate vlocaldate) {
		this.vlocaldate = vlocaldate;
	}
	public String getVmemo() {
		return vmemo;
	}
	public void setVmemo(String vmemo) {
		this.vmemo = vmemo;
	}
	
}
