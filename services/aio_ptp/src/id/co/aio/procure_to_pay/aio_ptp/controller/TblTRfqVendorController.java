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
import id.co.aio.procure_to_pay.aio_ptp.TblTRfqVendor;
import id.co.aio.procure_to_pay.aio_ptp.service.TblTRfqVendorService;


/**
 * Controller object for domain model class TblTRfqVendor.
 * @see TblTRfqVendor
 */
@RestController("aio_ptp.TblTRfqVendorController")
@Api(value = "TblTRfqVendorController", description = "Exposes APIs to work with TblTRfqVendor resource.")
@RequestMapping("/aio_ptp/TblTRfqVendor")
public class TblTRfqVendorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTRfqVendorController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTRfqVendorService")
	private TblTRfqVendorService tblTRfqVendorService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTRfqVendor instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTRfqVendor createTblTRfqVendor(@RequestBody TblTRfqVendor tblTrfqVendor) {
		LOGGER.debug("Create TblTRfqVendor with information: {}" , tblTrfqVendor);

		tblTrfqVendor = tblTRfqVendorService.create(tblTrfqVendor);
		LOGGER.debug("Created TblTRfqVendor with information: {}" , tblTrfqVendor);

	    return tblTrfqVendor;
	}

    @ApiOperation(value = "Returns the TblTRfqVendor instance associated with the given id.")
    @RequestMapping(value = "/{rfqvId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTRfqVendor getTblTRfqVendor(@PathVariable("rfqvId") Integer rfqvId) {
        LOGGER.debug("Getting TblTRfqVendor with id: {}" , rfqvId);

        TblTRfqVendor foundTblTRfqVendor = tblTRfqVendorService.getById(rfqvId);
        LOGGER.debug("TblTRfqVendor details with id: {}" , foundTblTRfqVendor);

        return foundTblTRfqVendor;
    }

    @ApiOperation(value = "Updates the TblTRfqVendor instance associated with the given id.")
    @RequestMapping(value = "/{rfqvId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTRfqVendor editTblTRfqVendor(@PathVariable("rfqvId") Integer rfqvId, @RequestBody TblTRfqVendor tblTrfqVendor) {
        LOGGER.debug("Editing TblTRfqVendor with id: {}" , tblTrfqVendor.getRfqvId());

        tblTrfqVendor.setRfqvId(rfqvId);
        tblTrfqVendor = tblTRfqVendorService.update(tblTrfqVendor);
        LOGGER.debug("TblTRfqVendor details with id: {}" , tblTrfqVendor);

        return tblTrfqVendor;
    }
    
    @ApiOperation(value = "Partially updates the TblTRfqVendor instance associated with the given id.")
    @RequestMapping(value = "/{rfqvId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTRfqVendor patchTblTRfqVendor(@PathVariable("rfqvId") Integer rfqvId, @RequestBody @MapTo(TblTRfqVendor.class) Map<String, Object> tblTrfqVendorPatch) {
        LOGGER.debug("Partially updating TblTRfqVendor with id: {}" , rfqvId);

        TblTRfqVendor tblTrfqVendor = tblTRfqVendorService.partialUpdate(rfqvId, tblTrfqVendorPatch);
        LOGGER.debug("TblTRfqVendor details after partial update: {}" , tblTrfqVendor);

        return tblTrfqVendor;
    }

    @ApiOperation(value = "Deletes the TblTRfqVendor instance associated with the given id.")
    @RequestMapping(value = "/{rfqvId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTRfqVendor(@PathVariable("rfqvId") Integer rfqvId) {
        LOGGER.debug("Deleting TblTRfqVendor with id: {}" , rfqvId);

        TblTRfqVendor deletedTblTRfqVendor = tblTRfqVendorService.delete(rfqvId);

        return deletedTblTRfqVendor != null;
    }

    /**
     * @deprecated Use {@link #findTblTRfqVendors(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTRfqVendor instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTRfqVendor> searchTblTRfqVendorsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTRfqVendors list by query filter:{}", (Object) queryFilters);
        return tblTRfqVendorService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTRfqVendor instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTRfqVendor> findTblTRfqVendors(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTRfqVendors list by filter:", query);
        return tblTRfqVendorService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTRfqVendor instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTRfqVendor> filterTblTRfqVendors(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTRfqVendors list by filter", query);
        return tblTRfqVendorService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTRfqVendors(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTRfqVendorService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTRfqVendorsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTRfqVendor.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTRfqVendorService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTRfqVendor instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTRfqVendors( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTRfqVendors");
		return tblTRfqVendorService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTRfqVendorAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTRfqVendorService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{rfqvId:.+}/tblTrfqVenQuotations", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblTrfqVenQuotations instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTRfqVenQuotation> findAssociatedTblTrfqVenQuotations(@PathVariable("rfqvId") Integer rfqvId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblTrfqVenQuotations");
        return tblTRfqVendorService.findAssociatedTblTrfqVenQuotations(rfqvId, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTRfqVendorService instance
	 */
	protected void setTblTRfqVendorService(TblTRfqVendorService service) {
		this.tblTRfqVendorService = service;
	}

}