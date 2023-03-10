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

import id.co.aio.procure_to_pay.avm2.TlMsForgotUser;
import id.co.aio.procure_to_pay.avm2.service.TlMsForgotUserService;


/**
 * Controller object for domain model class TlMsForgotUser.
 * @see TlMsForgotUser
 */
@RestController("AVM2.TlMsForgotUserController")
@Api(value = "TlMsForgotUserController", description = "Exposes APIs to work with TlMsForgotUser resource.")
@RequestMapping("/AVM2/TlMsForgotUser")
public class TlMsForgotUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsForgotUserController.class);

    @Autowired
	@Qualifier("AVM2.TlMsForgotUserService")
	private TlMsForgotUserService tlMsForgotUserService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new TlMsForgotUser instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsForgotUser createTlMsForgotUser(@RequestBody TlMsForgotUser tlMsForgotUser) {
		LOGGER.debug("Create TlMsForgotUser with information: {}" , tlMsForgotUser);

		tlMsForgotUser = tlMsForgotUserService.create(tlMsForgotUser);
		LOGGER.debug("Created TlMsForgotUser with information: {}" , tlMsForgotUser);

	    return tlMsForgotUser;
	}

    @ApiOperation(value = "Returns the TlMsForgotUser instance associated with the given id.")
    @RequestMapping(value = "/{abId:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsForgotUser getTlMsForgotUser(@PathVariable("abId") Integer abId) {
        LOGGER.debug("Getting TlMsForgotUser with id: {}" , abId);

        TlMsForgotUser foundTlMsForgotUser = tlMsForgotUserService.getById(abId);
        LOGGER.debug("TlMsForgotUser details with id: {}" , foundTlMsForgotUser);

        return foundTlMsForgotUser;
    }

    @ApiOperation(value = "Updates the TlMsForgotUser instance associated with the given id.")
    @RequestMapping(value = "/{abId:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsForgotUser editTlMsForgotUser(@PathVariable("abId") Integer abId, @RequestBody TlMsForgotUser tlMsForgotUser) {
        LOGGER.debug("Editing TlMsForgotUser with id: {}" , tlMsForgotUser.getAbId());

        tlMsForgotUser.setAbId(abId);
        tlMsForgotUser = tlMsForgotUserService.update(tlMsForgotUser);
        LOGGER.debug("TlMsForgotUser details with id: {}" , tlMsForgotUser);

        return tlMsForgotUser;
    }
    
    @ApiOperation(value = "Partially updates the TlMsForgotUser instance associated with the given id.")
    @RequestMapping(value = "/{abId:.+}", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public TlMsForgotUser patchTlMsForgotUser(@PathVariable("abId") Integer abId, @RequestBody @MapTo(TlMsForgotUser.class) Map<String, Object> tlMsForgotUserPatch) {
        LOGGER.debug("Partially updating TlMsForgotUser with id: {}" , abId);

        TlMsForgotUser tlMsForgotUser = tlMsForgotUserService.partialUpdate(abId, tlMsForgotUserPatch);
        LOGGER.debug("TlMsForgotUser details after partial update: {}" , tlMsForgotUser);

        return tlMsForgotUser;
    }

    @ApiOperation(value = "Deletes the TlMsForgotUser instance associated with the given id.")
    @RequestMapping(value = "/{abId:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteTlMsForgotUser(@PathVariable("abId") Integer abId) {
        LOGGER.debug("Deleting TlMsForgotUser with id: {}" , abId);

        TlMsForgotUser deletedTlMsForgotUser = tlMsForgotUserService.delete(abId);

        return deletedTlMsForgotUser != null;
    }

    /**
     * @deprecated Use {@link #findTlMsForgotUsers(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of TlMsForgotUser instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsForgotUser> searchTlMsForgotUsersByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering TlMsForgotUsers list by query filter:{}", (Object) queryFilters);
        return tlMsForgotUserService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsForgotUser instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsForgotUser> findTlMsForgotUsers(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsForgotUsers list by filter:", query);
        return tlMsForgotUserService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of TlMsForgotUser instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<TlMsForgotUser> filterTlMsForgotUsers(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering TlMsForgotUsers list by filter", query);
        return tlMsForgotUserService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportTlMsForgotUsers(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return tlMsForgotUserService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportTlMsForgotUsersAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = TlMsForgotUser.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> tlMsForgotUserService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of TlMsForgotUser instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countTlMsForgotUsers( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting TlMsForgotUsers");
		return tlMsForgotUserService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getTlMsForgotUserAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return tlMsForgotUserService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TlMsForgotUserService instance
	 */
	protected void setTlMsForgotUserService(TlMsForgotUserService service) {
		this.tlMsForgotUserService = service;
	}

}