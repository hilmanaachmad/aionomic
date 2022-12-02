/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master.controller;

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

import id.co.aio.procure_to_pay.sap_master.T023t;
import id.co.aio.procure_to_pay.sap_master.service.T023tService;


/**
 * Controller object for domain model class T023t.
 * @see T023t
 */
@RestController("sap_master.T023tController")
@Api(value = "T023tController", description = "Exposes APIs to work with T023t resource.")
@RequestMapping("/sap_master/T023t")
public class T023tController {

    private static final Logger LOGGER = LoggerFactory.getLogger(T023tController.class);

    @Autowired
	@Qualifier("sap_master.T023tService")
	private T023tService t023tService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new T023t instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public T023t createT023t(@RequestBody T023t t023t) {
		LOGGER.debug("Create T023t with information: {}" , t023t);

		t023t = t023tService.create(t023t);
		LOGGER.debug("Created T023t with information: {}" , t023t);

	    return t023t;
	}

    @ApiOperation(value = "Returns the T023t instance associated with the given id.")
    @RequestMapping(value = "/{matkl:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public T023t getT023t(@PathVariable("matkl") String matkl) {
        LOGGER.debug("Getting T023t with id: {}" , matkl);

        T023t foundT023t = t023tService.getById(matkl);
        LOGGER.debug("T023t details with id: {}" , foundT023t);

        return foundT023t;
    }

    @ApiOperation(value = "Updates the T023t instance associated with the given id.")
    @RequestMapping(value = "/{matkl:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public T023t editT023t(@PathVariable("matkl") String matkl, @RequestBody T023t t023t) {
        LOGGER.debug("Editing T023t with id: {}" , t023t.getMatkl());

        t023t.setMatkl(matkl);
        t023t = t023tService.update(t023t);
        LOGGER.debug("T023t details with id: {}" , t023t);

        return t023t;
    }
    
    @ApiOperation(value = "Partially updates the T023t instance associated with the given id.")
    @RequestMapping(value = "/{matkl:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public T023t patchT023t(@PathVariable("matkl") String matkl, @RequestBody @MapTo(T023t.class) Map<String, Object> t023tPatch) {
        LOGGER.debug("Partially updating T023t with id: {}" , matkl);

        T023t t023t = t023tService.partialUpdate(matkl, t023tPatch);
        LOGGER.debug("T023t details after partial update: {}" , t023t);

        return t023t;
    }

    @ApiOperation(value = "Deletes the T023t instance associated with the given id.")
    @RequestMapping(value = "/{matkl:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteT023t(@PathVariable("matkl") String matkl) {
        LOGGER.debug("Deleting T023t with id: {}" , matkl);

        T023t deletedT023t = t023tService.delete(matkl);

        return deletedT023t != null;
    }

    /**
     * @deprecated Use {@link #findT023ts(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of T023t instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<T023t> searchT023tsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering T023ts list by query filter:{}", (Object) queryFilters);
        return t023tService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of T023t instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<T023t> findT023ts(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering T023ts list by filter:", query);
        return t023tService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of T023t instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<T023t> filterT023ts(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering T023ts list by filter", query);
        return t023tService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportT023ts(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return t023tService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportT023tsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = T023t.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> t023tService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of T023t instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countT023ts( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting T023ts");
		return t023tService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getT023tAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return t023tService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service T023tService instance
	 */
	protected void setT023tService(T023tService service) {
		this.t023tService = service;
	}

}