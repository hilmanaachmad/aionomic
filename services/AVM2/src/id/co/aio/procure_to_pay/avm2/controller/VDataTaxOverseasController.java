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

import id.co.aio.procure_to_pay.avm2.VDataTaxOverseas;
import id.co.aio.procure_to_pay.avm2.service.VDataTaxOverseasService;


/**
 * Controller object for domain model class VDataTaxOverseas.
 * @see VDataTaxOverseas
 */
@RestController("AVM2.VDataTaxOverseasController")
@Api(value = "VDataTaxOverseasController", description = "Exposes APIs to work with VDataTaxOverseas resource.")
@RequestMapping("/AVM2/VDataTaxOverseas")
public class VDataTaxOverseasController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VDataTaxOverseasController.class);

    @Autowired
	@Qualifier("AVM2.VDataTaxOverseasService")
	private VDataTaxOverseasService vDataTaxOverseasService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new VDataTaxOverseas instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VDataTaxOverseas createVDataTaxOverseas(@RequestBody VDataTaxOverseas vdataTaxOverseas) {
		LOGGER.debug("Create VDataTaxOverseas with information: {}" , vdataTaxOverseas);

		vdataTaxOverseas = vDataTaxOverseasService.create(vdataTaxOverseas);
		LOGGER.debug("Created VDataTaxOverseas with information: {}" , vdataTaxOverseas);

	    return vdataTaxOverseas;
	}

    @ApiOperation(value = "Returns the VDataTaxOverseas instance associated with the given id.")
    @RequestMapping(value = "/{idTax:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VDataTaxOverseas getVDataTaxOverseas(@PathVariable("idTax") Integer idTax) {
        LOGGER.debug("Getting VDataTaxOverseas with id: {}" , idTax);

        VDataTaxOverseas foundVDataTaxOverseas = vDataTaxOverseasService.getById(idTax);
        LOGGER.debug("VDataTaxOverseas details with id: {}" , foundVDataTaxOverseas);

        return foundVDataTaxOverseas;
    }

    @ApiOperation(value = "Updates the VDataTaxOverseas instance associated with the given id.")
    @RequestMapping(value = "/{idTax:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VDataTaxOverseas editVDataTaxOverseas(@PathVariable("idTax") Integer idTax, @RequestBody VDataTaxOverseas vdataTaxOverseas) {
        LOGGER.debug("Editing VDataTaxOverseas with id: {}" , vdataTaxOverseas.getIdTax());

        vdataTaxOverseas.setIdTax(idTax);
        vdataTaxOverseas = vDataTaxOverseasService.update(vdataTaxOverseas);
        LOGGER.debug("VDataTaxOverseas details with id: {}" , vdataTaxOverseas);

        return vdataTaxOverseas;
    }
    
    @ApiOperation(value = "Partially updates the VDataTaxOverseas instance associated with the given id.")
    @RequestMapping(value = "/{idTax:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public VDataTaxOverseas patchVDataTaxOverseas(@PathVariable("idTax") Integer idTax, @RequestBody @MapTo(VDataTaxOverseas.class) Map<String, Object> vdataTaxOverseasPatch) {
        LOGGER.debug("Partially updating VDataTaxOverseas with id: {}" , idTax);

        VDataTaxOverseas vdataTaxOverseas = vDataTaxOverseasService.partialUpdate(idTax, vdataTaxOverseasPatch);
        LOGGER.debug("VDataTaxOverseas details after partial update: {}" , vdataTaxOverseas);

        return vdataTaxOverseas;
    }

    @ApiOperation(value = "Deletes the VDataTaxOverseas instance associated with the given id.")
    @RequestMapping(value = "/{idTax:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteVDataTaxOverseas(@PathVariable("idTax") Integer idTax) {
        LOGGER.debug("Deleting VDataTaxOverseas with id: {}" , idTax);

        VDataTaxOverseas deletedVDataTaxOverseas = vDataTaxOverseasService.delete(idTax);

        return deletedVDataTaxOverseas != null;
    }

    /**
     * @deprecated Use {@link #findVDataTaxOverseas(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of VDataTaxOverseas instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VDataTaxOverseas> searchVDataTaxOverseasByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering VDataTaxOverseas list by query filter:{}", (Object) queryFilters);
        return vDataTaxOverseasService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VDataTaxOverseas instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VDataTaxOverseas> findVDataTaxOverseas(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VDataTaxOverseas list by filter:", query);
        return vDataTaxOverseasService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of VDataTaxOverseas instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<VDataTaxOverseas> filterVDataTaxOverseas(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering VDataTaxOverseas list by filter", query);
        return vDataTaxOverseasService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportVDataTaxOverseas(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return vDataTaxOverseasService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportVDataTaxOverseasAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = VDataTaxOverseas.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> vDataTaxOverseasService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of VDataTaxOverseas instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countVDataTaxOverseas( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting VDataTaxOverseas");
		return vDataTaxOverseasService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getVDataTaxOverseasAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return vDataTaxOverseasService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service VDataTaxOverseasService instance
	 */
	protected void setVDataTaxOverseasService(VDataTaxOverseasService service) {
		this.vDataTaxOverseasService = service;
	}

}