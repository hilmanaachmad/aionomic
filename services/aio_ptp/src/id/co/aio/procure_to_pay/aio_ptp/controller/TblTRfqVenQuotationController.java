/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.controller;

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

import id.co.aio.procure_to_pay.aio_ptp.TblTRfqVenQuotation;
import id.co.aio.procure_to_pay.aio_ptp.service.TblTRfqVenQuotationService;


/**
 * Controller object for domain model class TblTRfqVenQuotation.
 * @see TblTRfqVenQuotation
 */
@RestController("aio_ptp.TblTRfqVenQuotationController")
@Api(value = "TblTRfqVenQuotationController", description = "Exposes APIs to work with TblTRfqVenQuotation resource.")
@RequestMapping("/aio_ptp/TblTRfqVenQuotation")
public class TblTRfqVenQuotationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTRfqVenQuotationController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTRfqVenQuotationService")
	private TblTRfqVenQuotationService tblTRfqVenQuotationService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTRfqVenQuotation instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTRfqVenQuotation createTblTRfqVenQuotation(@RequestBody TblTRfqVenQuotation tblTrfqVenQuotation) {
		LOGGER.debug("Create TblTRfqVenQuotation with information: {}" , tblTrfqVenQuotation);

		tblTrfqVenQuotation = tblTRfqVenQuotationService.create(tblTrfqVenQuotation);
		LOGGER.debug("Created TblTRfqVenQuotation with information: {}" , tblTrfqVenQuotation);

	    return tblTrfqVenQuotation;
	}

    @ApiOperation(value = "Returns the TblTRfqVenQuotation instance associated with the given id.")
    @RequestMapping(value = "/{liqId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTRfqVenQuotation getTblTRfqVenQuotation(@PathVariable("liqId") Integer liqId) {
        LOGGER.debug("Getting TblTRfqVenQuotation with id: {}" , liqId);

        TblTRfqVenQuotation foundTblTRfqVenQuotation = tblTRfqVenQuotationService.getById(liqId);
        LOGGER.debug("TblTRfqVenQuotation details with id: {}" , foundTblTRfqVenQuotation);

        return foundTblTRfqVenQuotation;
    }

    @ApiOperation(value = "Updates the TblTRfqVenQuotation instance associated with the given id.")
    @RequestMapping(value = "/{liqId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTRfqVenQuotation editTblTRfqVenQuotation(@PathVariable("liqId") Integer liqId, @RequestBody TblTRfqVenQuotation tblTrfqVenQuotation) {
        LOGGER.debug("Editing TblTRfqVenQuotation with id: {}" , tblTrfqVenQuotation.getLiqId());

        tblTrfqVenQuotation.setLiqId(liqId);
        tblTrfqVenQuotation = tblTRfqVenQuotationService.update(tblTrfqVenQuotation);
        LOGGER.debug("TblTRfqVenQuotation details with id: {}" , tblTrfqVenQuotation);

        return tblTrfqVenQuotation;
    }
    
    @ApiOperation(value = "Partially updates the TblTRfqVenQuotation instance associated with the given id.")
    @RequestMapping(value = "/{liqId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTRfqVenQuotation patchTblTRfqVenQuotation(@PathVariable("liqId") Integer liqId, @RequestBody @MapTo(TblTRfqVenQuotation.class) Map<String, Object> tblTrfqVenQuotationPatch) {
        LOGGER.debug("Partially updating TblTRfqVenQuotation with id: {}" , liqId);

        TblTRfqVenQuotation tblTrfqVenQuotation = tblTRfqVenQuotationService.partialUpdate(liqId, tblTrfqVenQuotationPatch);
        LOGGER.debug("TblTRfqVenQuotation details after partial update: {}" , tblTrfqVenQuotation);

        return tblTrfqVenQuotation;
    }

    @ApiOperation(value = "Deletes the TblTRfqVenQuotation instance associated with the given id.")
    @RequestMapping(value = "/{liqId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTRfqVenQuotation(@PathVariable("liqId") Integer liqId) {
        LOGGER.debug("Deleting TblTRfqVenQuotation with id: {}" , liqId);

        TblTRfqVenQuotation deletedTblTRfqVenQuotation = tblTRfqVenQuotationService.delete(liqId);

        return deletedTblTRfqVenQuotation != null;
    }

    /**
     * @deprecated Use {@link #findTblTRfqVenQuotations(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTRfqVenQuotation instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTRfqVenQuotation> searchTblTRfqVenQuotationsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTRfqVenQuotations list by query filter:{}", (Object) queryFilters);
        return tblTRfqVenQuotationService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTRfqVenQuotation instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTRfqVenQuotation> findTblTRfqVenQuotations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTRfqVenQuotations list by filter:", query);
        return tblTRfqVenQuotationService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTRfqVenQuotation instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTRfqVenQuotation> filterTblTRfqVenQuotations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTRfqVenQuotations list by filter", query);
        return tblTRfqVenQuotationService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTRfqVenQuotations(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTRfqVenQuotationService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTRfqVenQuotationsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTRfqVenQuotation.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTRfqVenQuotationService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTRfqVenQuotation instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTRfqVenQuotations( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTRfqVenQuotations");
		return tblTRfqVenQuotationService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTRfqVenQuotationAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTRfqVenQuotationService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTRfqVenQuotationService instance
	 */
	protected void setTblTRfqVenQuotationService(TblTRfqVenQuotationService service) {
		this.tblTRfqVenQuotationService = service;
	}

}