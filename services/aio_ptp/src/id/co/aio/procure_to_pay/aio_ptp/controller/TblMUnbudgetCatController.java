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

import id.co.aio.procure_to_pay.aio_ptp.TblMUnbudgetCat;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetAdditional;
import id.co.aio.procure_to_pay.aio_ptp.TblTBudgetDetails;
import id.co.aio.procure_to_pay.aio_ptp.service.TblMUnbudgetCatService;


/**
 * Controller object for domain model class TblMUnbudgetCat.
 * @see TblMUnbudgetCat
 */
@RestController("aio_ptp.TblMUnbudgetCatController")
@Api(value = "TblMUnbudgetCatController", description = "Exposes APIs to work with TblMUnbudgetCat resource.")
@RequestMapping("/aio_ptp/TblMUnbudgetCat")
public class TblMUnbudgetCatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMUnbudgetCatController.class);

    @Autowired
	@Qualifier("aio_ptp.TblMUnbudgetCatService")
	private TblMUnbudgetCatService tblMUnbudgetCatService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblMUnbudgetCat instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMUnbudgetCat createTblMUnbudgetCat(@RequestBody TblMUnbudgetCat tblMunbudgetCat) {
		LOGGER.debug("Create TblMUnbudgetCat with information: {}" , tblMunbudgetCat);

		tblMunbudgetCat = tblMUnbudgetCatService.create(tblMunbudgetCat);
		LOGGER.debug("Created TblMUnbudgetCat with information: {}" , tblMunbudgetCat);

	    return tblMunbudgetCat;
	}

    @ApiOperation(value = "Returns the TblMUnbudgetCat instance associated with the given id.")
    @RequestMapping(value = "/{ubCatId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMUnbudgetCat getTblMUnbudgetCat(@PathVariable("ubCatId") Integer ubCatId) {
        LOGGER.debug("Getting TblMUnbudgetCat with id: {}" , ubCatId);

        TblMUnbudgetCat foundTblMUnbudgetCat = tblMUnbudgetCatService.getById(ubCatId);
        LOGGER.debug("TblMUnbudgetCat details with id: {}" , foundTblMUnbudgetCat);

        return foundTblMUnbudgetCat;
    }

    @ApiOperation(value = "Updates the TblMUnbudgetCat instance associated with the given id.")
    @RequestMapping(value = "/{ubCatId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMUnbudgetCat editTblMUnbudgetCat(@PathVariable("ubCatId") Integer ubCatId, @RequestBody TblMUnbudgetCat tblMunbudgetCat) {
        LOGGER.debug("Editing TblMUnbudgetCat with id: {}" , tblMunbudgetCat.getUbCatId());

        tblMunbudgetCat.setUbCatId(ubCatId);
        tblMunbudgetCat = tblMUnbudgetCatService.update(tblMunbudgetCat);
        LOGGER.debug("TblMUnbudgetCat details with id: {}" , tblMunbudgetCat);

        return tblMunbudgetCat;
    }
    
    @ApiOperation(value = "Partially updates the TblMUnbudgetCat instance associated with the given id.")
    @RequestMapping(value = "/{ubCatId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMUnbudgetCat patchTblMUnbudgetCat(@PathVariable("ubCatId") Integer ubCatId, @RequestBody @MapTo(TblMUnbudgetCat.class) Map<String, Object> tblMunbudgetCatPatch) {
        LOGGER.debug("Partially updating TblMUnbudgetCat with id: {}" , ubCatId);

        TblMUnbudgetCat tblMunbudgetCat = tblMUnbudgetCatService.partialUpdate(ubCatId, tblMunbudgetCatPatch);
        LOGGER.debug("TblMUnbudgetCat details after partial update: {}" , tblMunbudgetCat);

        return tblMunbudgetCat;
    }

    @ApiOperation(value = "Deletes the TblMUnbudgetCat instance associated with the given id.")
    @RequestMapping(value = "/{ubCatId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblMUnbudgetCat(@PathVariable("ubCatId") Integer ubCatId) {
        LOGGER.debug("Deleting TblMUnbudgetCat with id: {}" , ubCatId);

        TblMUnbudgetCat deletedTblMUnbudgetCat = tblMUnbudgetCatService.delete(ubCatId);

        return deletedTblMUnbudgetCat != null;
    }

    /**
     * @deprecated Use {@link #findTblMUnbudgetCats(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblMUnbudgetCat instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMUnbudgetCat> searchTblMUnbudgetCatsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblMUnbudgetCats list by query filter:{}", (Object) queryFilters);
        return tblMUnbudgetCatService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMUnbudgetCat instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMUnbudgetCat> findTblMUnbudgetCats(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMUnbudgetCats list by filter:", query);
        return tblMUnbudgetCatService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMUnbudgetCat instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMUnbudgetCat> filterTblMUnbudgetCats(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMUnbudgetCats list by filter", query);
        return tblMUnbudgetCatService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblMUnbudgetCats(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblMUnbudgetCatService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblMUnbudgetCatsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblMUnbudgetCat.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblMUnbudgetCatService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblMUnbudgetCat instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblMUnbudgetCats( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblMUnbudgetCats");
		return tblMUnbudgetCatService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblMUnbudgetCatAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblMUnbudgetCatService.getAggregatedValues(aggregationInfo, pageable);
    }

    @RequestMapping(value="/{ubCatId:.+}/tblTbudgetAdditionals", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblTbudgetAdditionals instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetAdditional> findAssociatedTblTbudgetAdditionals(@PathVariable("ubCatId") Integer ubCatId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblTbudgetAdditionals");
        return tblMUnbudgetCatService.findAssociatedTblTbudgetAdditionals(ubCatId, pageable);
    }

    @RequestMapping(value="/{ubCatId:.+}/tblTbudgetDetailses", method=RequestMethod.GET)
    @ApiOperation(value = "Gets the tblTbudgetDetailses instance associated with the given id.")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTBudgetDetails> findAssociatedTblTbudgetDetailses(@PathVariable("ubCatId") Integer ubCatId, Pageable pageable) {

        LOGGER.debug("Fetching all associated tblTbudgetDetailses");
        return tblMUnbudgetCatService.findAssociatedTblTbudgetDetailses(ubCatId, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblMUnbudgetCatService instance
	 */
	protected void setTblMUnbudgetCatService(TblMUnbudgetCatService service) {
		this.tblMUnbudgetCatService = service;
	}

}