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

import id.co.aio.procure_to_pay.aio_ptp.TblTPo;
import id.co.aio.procure_to_pay.aio_ptp.service.TblTPoService;


/**
 * Controller object for domain model class TblTPo.
 * @see TblTPo
 */
@RestController("aio_ptp.TblTPoController")
@Api(value = "TblTPoController", description = "Exposes APIs to work with TblTPo resource.")
@RequestMapping("/aio_ptp/TblTPo")
public class TblTPoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTPoController.class);

    @Autowired
	@Qualifier("aio_ptp.TblTPoService")
	private TblTPoService tblTPoService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblTPo instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPo createTblTPo(@RequestBody TblTPo tblTpo) {
		LOGGER.debug("Create TblTPo with information: {}" , tblTpo);

		tblTpo = tblTPoService.create(tblTpo);
		LOGGER.debug("Created TblTPo with information: {}" , tblTpo);

	    return tblTpo;
	}

    @ApiOperation(value = "Returns the TblTPo instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPo getTblTPo(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting TblTPo with id: {}" , id);

        TblTPo foundTblTPo = tblTPoService.getById(id);
        LOGGER.debug("TblTPo details with id: {}" , foundTblTPo);

        return foundTblTPo;
    }

    @ApiOperation(value = "Updates the TblTPo instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPo editTblTPo(@PathVariable("id") Integer id, @RequestBody TblTPo tblTpo) {
        LOGGER.debug("Editing TblTPo with id: {}" , tblTpo.getId());

        tblTpo.setId(id);
        tblTpo = tblTPoService.update(tblTpo);
        LOGGER.debug("TblTPo details with id: {}" , tblTpo);

        return tblTpo;
    }
    
    @ApiOperation(value = "Partially updates the TblTPo instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblTPo patchTblTPo(@PathVariable("id") Integer id, @RequestBody @MapTo(TblTPo.class) Map<String, Object> tblTpoPatch) {
        LOGGER.debug("Partially updating TblTPo with id: {}" , id);

        TblTPo tblTpo = tblTPoService.partialUpdate(id, tblTpoPatch);
        LOGGER.debug("TblTPo details after partial update: {}" , tblTpo);

        return tblTpo;
    }

    @ApiOperation(value = "Deletes the TblTPo instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblTPo(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting TblTPo with id: {}" , id);

        TblTPo deletedTblTPo = tblTPoService.delete(id);

        return deletedTblTPo != null;
    }

    /**
     * @deprecated Use {@link #findTblTPos(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblTPo instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPo> searchTblTPosByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblTPos list by query filter:{}", (Object) queryFilters);
        return tblTPoService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTPo instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPo> findTblTPos(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTPos list by filter:", query);
        return tblTPoService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblTPo instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblTPo> filterTblTPos(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblTPos list by filter", query);
        return tblTPoService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblTPos(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblTPoService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblTPosAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblTPo.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblTPoService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblTPo instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblTPos( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblTPos");
		return tblTPoService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblTPoAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblTPoService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblTPoService instance
	 */
	protected void setTblTPoService(TblTPoService service) {
		this.tblTPoService = service;
	}

}