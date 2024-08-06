package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class CompanyDTO {

    private Integer id;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 200, message = "{size.must.be.between}")
    private String corporateName;

    @Size(min = 1, max = 200, message = "{size.must.be.between}")
    private String tradeName;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 14, max = 14, message = "{size.must.be.between}")
    private String cnpj;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 200, message = "{size.must.be.between}")
    private String bookkeeperBank;

    @NotNull(message = "{notBlank.must.not.be.blank}")
    private Integer sectorId;

    public CompanyDTO() {
    }

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.corporateName = company.getCorporateName();
        this.tradeName = company.getTradeName();
        this.cnpj = company.getCnpj();
        this.bookkeeperBank = company.getBookkeeperBank();
        this.sectorId = company.getSector().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getBookkeeperBank() {
        return bookkeeperBank;
    }

    public void setBookkeeperBank(String bookkeeperBank) {
        this.bookkeeperBank = bookkeeperBank;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        CompanyDTO that = (CompanyDTO) object;
        return Objects.equals(id, that.id) && Objects.equals(corporateName, that.corporateName) && Objects.equals(tradeName, that.tradeName) && Objects.equals(cnpj, that.cnpj) && Objects.equals(bookkeeperBank, that.bookkeeperBank) && Objects.equals(sectorId, that.sectorId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(corporateName);
        result = 31 * result + Objects.hashCode(tradeName);
        result = 31 * result + Objects.hashCode(cnpj);
        result = 31 * result + Objects.hashCode(bookkeeperBank);
        result = 31 * result + Objects.hashCode(sectorId);
        return result;
    }
}