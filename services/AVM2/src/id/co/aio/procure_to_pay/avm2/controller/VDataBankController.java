/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

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

import id.co.aio.procure_to_pay.avm2.VDataBank;
import id.co.aio.procure_to_pay.avm2.service.VDataBankService;


/**
 * Controller object for domain model class VDataBank.
 * @see VDataBank
 */
@RestController("AVM2.VDataBankController")
@Api(value = "VDataBankController", description = "Exposes APIs to work with VDataBank resource.")
@RequestMapping("/AVM2/VDataBank")
public class VDataBankController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VDataBankController.class);

    @Autowired
	@Qualifier("AVM2.VDataBankService")
	private VDataBankService vDataBankService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new VDataBank instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VDataBank createVDataBank(@RequestBody VDataBank vdataBank) {
		LOGGER.debug("Create VDataBank with information: {}" , vdataBank);

		vdataBank = vDataBankService.create(vdataBank);
		LOGGER.debug("Created VDataBank with information: {}" , vdataBank);

	    return vdataBank;
	}

    @ApiOperation(value = "Returns the VDataBank instance associated with the given id.")
    @RequestMapping(value = "/{idBank:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VDataBank getVDataBank(@PathVariable("idBank") Integer idBank) {
        LOGGER.debug("Getting VDataBank with id: {}" , idBank);

        VDataBank foundVDataBank = vDataBankService.getById(idBank);
        LOGGER.debug("VDataBank details with id: {}" , foundVDataBank);

        return foundVDataBank;
    }

    @ApiOperation(value = "Updates the VDataBank instance associated with the given id.")
    @RequestMapping(value = "/{idBank:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VDataBank editVDataBank(@PathVariable("idBank") Integer idBank, @RequestBody VDataBank vdataBank) {
        LOGGER.debug("Editing VDataBank with id: {}" , vdataBank.getIdBank());

        vdataBank.setIdBank(idBank);
        vdataBank = vDataBankService.update(vdataBank);
        LOGGER.debug("VDataBank details with id: {}" , vdataBank);

        return vdataBank;
    }
    
    @ApiOperation(value = "Partially updates the VDataBank instance associated with the given id.")
    @RequestMapping(value = "/{idBank:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VDataBank patchVDataBank(@PathVariable("idBank") Integer idBank, @RequestBody @MapTo(VDataBank.class) Map<String, Object> vdataBankPatch) {
        LOGGER.debug("Partially updating VDataBank with id: {}" , idBank);

        VDataBank vdataBank = vDataBankService.partialUpdate(idBank, vdataBankPatch);
        LOGGER.debug("VDataBank details after partial update: {}" , vdataBank);

        return vdataBank;
    }

    @ApiOperation(value = "Deletes the VDataBank instance associated with the given id.")
    @RequestMapping(value = "/{idBank:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVDataBank(@PathVariable("idBank") Integer idBank) {
        LOGGER.debug("Deleting VDataBank with id: {}" , idBank);

        VDataBank deletedVDataBank = vDataBankService.delete(idBank);

        return deletedVDataBank != null;
    }

    /**
     * @deprecated Use {@link #findVDataBanks(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of VDataBank instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VDataBank> searchVDataBanksByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering VDataBanks list by query filter:{}", (Object) queryFilters);
        return vDataBankService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VDataBank instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VDataBank> findVDataBanks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VDataBanks list by filter:", query);
        return vDataBankService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VDataBank instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VDataBank> filterVDataBanks(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VDataBanks list by filter", query);
        return vDataBankService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVDataBanks(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return vDataBankService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportVDataBanksAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = VDataBank.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> vDataBankService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of VDataBank instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countVDataBanks( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting VDataBanks");
		return vDataBankService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getVDataBankAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return vDataBankService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VDataBankService instance
	 */
	protected void setVDataBankService(VDataBankService service) {
		this.vDataBankService = service;
	}

}