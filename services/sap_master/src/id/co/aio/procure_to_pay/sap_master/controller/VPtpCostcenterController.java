/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.sql.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.MapTo;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import id.co.aio.procure_to_pay.sap_master.VPtpCostcenter;
import id.co.aio.procure_to_pay.sap_master.VPtpCostcenterId;
import id.co.aio.procure_to_pay.sap_master.service.VPtpCostcenterService;


/**
 * Controller object for domain model class VPtpCostcenter.
 * @see VPtpCostcenter
 */
@RestController("sap_master.VPtpCostcenterController")
@Api(value = "VPtpCostcenterController", description = "Exposes APIs to work with VPtpCostcenter resource.")
@RequestMapping("/sap_master/VPtpCostcenter")
public class VPtpCostcenterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VPtpCostcenterController.class);

    @Autowired
	@Qualifier("sap_master.VPtpCostcenterService")
	private VPtpCostcenterService vPtpCostcenterService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new VPtpCostcenter instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPtpCostcenter createVPtpCostcenter(@RequestBody VPtpCostcenter vptpCostcenter) {
		LOGGER.debug("Create VPtpCostcenter with information: {}" , vptpCostcenter);

		vptpCostcenter = vPtpCostcenterService.create(vptpCostcenter);
		LOGGER.debug("Created VPtpCostcenter with information: {}" , vptpCostcenter);

	    return vptpCostcenter;
	}

    @ApiOperation(value = "Returns the VPtpCostcenter instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPtpCostcenter getVPtpCostcenter(@RequestParam("costCenterCategory") String costCenterCategory, @RequestParam("shortText") String shortText, @RequestParam("werks") String werks, @RequestParam("profitCenter") String profitCenter, @RequestParam("gsber") String gsber, @RequestParam("personResponsible") String personResponsible, @RequestParam("validFromDate") Date validFromDate, @RequestParam("validToDate") Date validToDate, @RequestParam("controllingArea") String controllingArea, @RequestParam("longText") String longText, @RequestParam("costCenter") String costCenter, @RequestParam("companyCode") String companyCode, @RequestParam("descOfMerchantId") String descOfMerchantId) {

        VPtpCostcenterId vptpcostcenterId = new VPtpCostcenterId();
        vptpcostcenterId.setCostCenterCategory(costCenterCategory);
        vptpcostcenterId.setShortText(shortText);
        vptpcostcenterId.setWerks(werks);
        vptpcostcenterId.setProfitCenter(profitCenter);
        vptpcostcenterId.setGsber(gsber);
        vptpcostcenterId.setPersonResponsible(personResponsible);
        vptpcostcenterId.setValidFromDate(validFromDate);
        vptpcostcenterId.setValidToDate(validToDate);
        vptpcostcenterId.setControllingArea(controllingArea);
        vptpcostcenterId.setLongText(longText);
        vptpcostcenterId.setCostCenter(costCenter);
        vptpcostcenterId.setCompanyCode(companyCode);
        vptpcostcenterId.setDescOfMerchantId(descOfMerchantId);

        LOGGER.debug("Getting VPtpCostcenter with id: {}" , vptpcostcenterId);
        VPtpCostcenter vptpCostcenter = vPtpCostcenterService.getById(vptpcostcenterId);
        LOGGER.debug("VPtpCostcenter details with id: {}" , vptpCostcenter);

        return vptpCostcenter;
    }



    @ApiOperation(value = "Updates the VPtpCostcenter instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPtpCostcenter editVPtpCostcenter(@RequestParam("costCenterCategory") String costCenterCategory, @RequestParam("shortText") String shortText, @RequestParam("werks") String werks, @RequestParam("profitCenter") String profitCenter, @RequestParam("gsber") String gsber, @RequestParam("personResponsible") String personResponsible, @RequestParam("validFromDate") Date validFromDate, @RequestParam("validToDate") Date validToDate, @RequestParam("controllingArea") String controllingArea, @RequestParam("longText") String longText, @RequestParam("costCenter") String costCenter, @RequestParam("companyCode") String companyCode, @RequestParam("descOfMerchantId") String descOfMerchantId, @RequestBody VPtpCostcenter vptpCostcenter) {

        vptpCostcenter.setCostCenterCategory(costCenterCategory);
        vptpCostcenter.setShortText(shortText);
        vptpCostcenter.setWerks(werks);
        vptpCostcenter.setProfitCenter(profitCenter);
        vptpCostcenter.setGsber(gsber);
        vptpCostcenter.setPersonResponsible(personResponsible);
        vptpCostcenter.setValidFromDate(validFromDate);
        vptpCostcenter.setValidToDate(validToDate);
        vptpCostcenter.setControllingArea(controllingArea);
        vptpCostcenter.setLongText(longText);
        vptpCostcenter.setCostCenter(costCenter);
        vptpCostcenter.setCompanyCode(companyCode);
        vptpCostcenter.setDescOfMerchantId(descOfMerchantId);

        LOGGER.debug("VPtpCostcenter details with id is updated with: {}" , vptpCostcenter);

        return vPtpCostcenterService.update(vptpCostcenter);
    }

	@ApiOperation(value = "Partially updates the  VPtpCostcenter instance associated with the given composite-id.")
	@RequestMapping(value = "/composite-id", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VPtpCostcenter patchVPtpCostcenter(@RequestParam("costCenterCategory") String costCenterCategory, @RequestParam("shortText") String shortText, @RequestParam("werks") String werks, @RequestParam("profitCenter") String profitCenter, @RequestParam("gsber") String gsber, @RequestParam("personResponsible") String personResponsible, @RequestParam("validFromDate") Date validFromDate, @RequestParam("validToDate") Date validToDate, @RequestParam("controllingArea") String controllingArea, @RequestParam("longText") String longText, @RequestParam("costCenter") String costCenter, @RequestParam("companyCode") String companyCode, @RequestParam("descOfMerchantId") String descOfMerchantId, @RequestBody @MapTo(VPtpCostcenter.class) Map<String, Object> vptpCostcenterPatch) {

        VPtpCostcenterId vptpcostcenterId = new VPtpCostcenterId();
        vptpcostcenterId.setCostCenterCategory(costCenterCategory);
        vptpcostcenterId.setShortText(shortText);
        vptpcostcenterId.setWerks(werks);
        vptpcostcenterId.setProfitCenter(profitCenter);
        vptpcostcenterId.setGsber(gsber);
        vptpcostcenterId.setPersonResponsible(personResponsible);
        vptpcostcenterId.setValidFromDate(validFromDate);
        vptpcostcenterId.setValidToDate(validToDate);
        vptpcostcenterId.setControllingArea(controllingArea);
        vptpcostcenterId.setLongText(longText);
        vptpcostcenterId.setCostCenter(costCenter);
        vptpcostcenterId.setCompanyCode(companyCode);
        vptpcostcenterId.setDescOfMerchantId(descOfMerchantId);
        LOGGER.debug("Partially updating VPtpCostcenter with id: {}" , vptpcostcenterId);

        VPtpCostcenter vptpCostcenter = vPtpCostcenterService.partialUpdate(vptpcostcenterId, vptpCostcenterPatch);
        LOGGER.debug("VPtpCostcenter details after partial update: {}" , vptpCostcenter);

        return vptpCostcenter;
    }


    @ApiOperation(value = "Deletes the VPtpCostcenter instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVPtpCostcenter(@RequestParam("costCenterCategory") String costCenterCategory, @RequestParam("shortText") String shortText, @RequestParam("werks") String werks, @RequestParam("profitCenter") String profitCenter, @RequestParam("gsber") String gsber, @RequestParam("personResponsible") String personResponsible, @RequestParam("validFromDate") Date validFromDate, @RequestParam("validToDate") Date validToDate, @RequestParam("controllingArea") String controllingArea, @RequestParam("longText") String longText, @RequestParam("costCenter") String costCenter, @RequestParam("companyCode") String companyCode, @RequestParam("descOfMerchantId") String descOfMerchantId) {

        VPtpCostcenterId vptpcostcenterId = new VPtpCostcenterId();
        vptpcostcenterId.setCostCenterCategory(costCenterCategory);
        vptpcostcenterId.setShortText(shortText);
        vptpcostcenterId.setWerks(werks);
        vptpcostcenterId.setProfitCenter(profitCenter);
        vptpcostcenterId.setGsber(gsber);
        vptpcostcenterId.setPersonResponsible(personResponsible);
        vptpcostcenterId.setValidFromDate(validFromDate);
        vptpcostcenterId.setValidToDate(validToDate);
        vptpcostcenterId.setControllingArea(controllingArea);
        vptpcostcenterId.setLongText(longText);
        vptpcostcenterId.setCostCenter(costCenter);
        vptpcostcenterId.setCompanyCode(companyCode);
        vptpcostcenterId.setDescOfMerchantId(descOfMerchantId);

        LOGGER.debug("Deleting VPtpCostcenter with id: {}" , vptpcostcenterId);
        VPtpCostcenter vptpCostcenter = vPtpCostcenterService.delete(vptpcostcenterId);

        return vptpCostcenter != null;
    }


    /**
     * @deprecated Use {@link #findVPtpCostcenters(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of VPtpCostcenter instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPtpCostcenter> searchVPtpCostcentersByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering VPtpCostcenters list by query filter:{}", (Object) queryFilters);
        return vPtpCostcenterService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VPtpCostcenter instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPtpCostcenter> findVPtpCostcenters(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VPtpCostcenters list by filter:", query);
        return vPtpCostcenterService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VPtpCostcenter instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VPtpCostcenter> filterVPtpCostcenters(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VPtpCostcenters list by filter", query);
        return vPtpCostcenterService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVPtpCostcenters(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return vPtpCostcenterService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportVPtpCostcentersAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = VPtpCostcenter.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> vPtpCostcenterService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of VPtpCostcenter instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countVPtpCostcenters( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting VPtpCostcenters");
		return vPtpCostcenterService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getVPtpCostcenterAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return vPtpCostcenterService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VPtpCostcenterService instance
	 */
	protected void setVPtpCostcenterService(VPtpCostcenterService service) {
		this.vPtpCostcenterService = service;
	}

}