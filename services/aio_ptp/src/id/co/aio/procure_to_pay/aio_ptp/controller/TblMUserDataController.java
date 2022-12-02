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

import id.co.aio.procure_to_pay.aio_ptp.TblMUserData;
import id.co.aio.procure_to_pay.aio_ptp.service.TblMUserDataService;


/**
 * Controller object for domain model class TblMUserData.
 * @see TblMUserData
 */
@RestController("aio_ptp.TblMUserDataController")
@Api(value = "TblMUserDataController", description = "Exposes APIs to work with TblMUserData resource.")
@RequestMapping("/aio_ptp/TblMUserData")
public class TblMUserDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMUserDataController.class);

    @Autowired
	@Qualifier("aio_ptp.TblMUserDataService")
	private TblMUserDataService tblMUserDataService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TblMUserData instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMUserData createTblMUserData(@RequestBody TblMUserData tblMuserData) {
		LOGGER.debug("Create TblMUserData with information: {}" , tblMuserData);

		tblMuserData = tblMUserDataService.create(tblMuserData);
		LOGGER.debug("Created TblMUserData with information: {}" , tblMuserData);

	    return tblMuserData;
	}

    @ApiOperation(value = "Returns the TblMUserData instance associated with the given id.")
    @RequestMapping(value = "/{udatId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMUserData getTblMUserData(@PathVariable("udatId") Integer udatId) {
        LOGGER.debug("Getting TblMUserData with id: {}" , udatId);

        TblMUserData foundTblMUserData = tblMUserDataService.getById(udatId);
        LOGGER.debug("TblMUserData details with id: {}" , foundTblMUserData);

        return foundTblMUserData;
    }

    @ApiOperation(value = "Updates the TblMUserData instance associated with the given id.")
    @RequestMapping(value = "/{udatId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMUserData editTblMUserData(@PathVariable("udatId") Integer udatId, @RequestBody TblMUserData tblMuserData) {
        LOGGER.debug("Editing TblMUserData with id: {}" , tblMuserData.getUdatId());

        tblMuserData.setUdatId(udatId);
        tblMuserData = tblMUserDataService.update(tblMuserData);
        LOGGER.debug("TblMUserData details with id: {}" , tblMuserData);

        return tblMuserData;
    }
    
    @ApiOperation(value = "Partially updates the TblMUserData instance associated with the given id.")
    @RequestMapping(value = "/{udatId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TblMUserData patchTblMUserData(@PathVariable("udatId") Integer udatId, @RequestBody @MapTo(TblMUserData.class) Map<String, Object> tblMuserDataPatch) {
        LOGGER.debug("Partially updating TblMUserData with id: {}" , udatId);

        TblMUserData tblMuserData = tblMUserDataService.partialUpdate(udatId, tblMuserDataPatch);
        LOGGER.debug("TblMUserData details after partial update: {}" , tblMuserData);

        return tblMuserData;
    }

    @ApiOperation(value = "Deletes the TblMUserData instance associated with the given id.")
    @RequestMapping(value = "/{udatId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTblMUserData(@PathVariable("udatId") Integer udatId) {
        LOGGER.debug("Deleting TblMUserData with id: {}" , udatId);

        TblMUserData deletedTblMUserData = tblMUserDataService.delete(udatId);

        return deletedTblMUserData != null;
    }

    /**
     * @deprecated Use {@link #findTblMUserDatas(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TblMUserData instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMUserData> searchTblMUserDatasByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TblMUserDatas list by query filter:{}", (Object) queryFilters);
        return tblMUserDataService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMUserData instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMUserData> findTblMUserDatas(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMUserDatas list by filter:", query);
        return tblMUserDataService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TblMUserData instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TblMUserData> filterTblMUserDatas(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TblMUserDatas list by filter", query);
        return tblMUserDataService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTblMUserDatas(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tblMUserDataService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTblMUserDatasAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TblMUserData.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tblMUserDataService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TblMUserData instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTblMUserDatas( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TblMUserDatas");
		return tblMUserDataService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTblMUserDataAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tblMUserDataService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TblMUserDataService instance
	 */
	protected void setTblMUserDataService(TblMUserDataService service) {
		this.tblMUserDataService = service;
	}

}