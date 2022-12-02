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

import id.co.aio.procure_to_pay.avm2.CategoryVendor;
import id.co.aio.procure_to_pay.avm2.CategoryVendorId;
import id.co.aio.procure_to_pay.avm2.service.CategoryVendorService;


/**
 * Controller object for domain model class CategoryVendor.
 * @see CategoryVendor
 */
@RestController("AVM2.CategoryVendorController")
@Api(value = "CategoryVendorController", description = "Exposes APIs to work with CategoryVendor resource.")
@RequestMapping("/AVM2/CategoryVendor")
public class CategoryVendorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryVendorController.class);

    @Autowired
	@Qualifier("AVM2.CategoryVendorService")
	private CategoryVendorService categoryVendorService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new CategoryVendor instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CategoryVendor createCategoryVendor(@RequestBody CategoryVendor categoryVendor) {
		LOGGER.debug("Create CategoryVendor with information: {}" , categoryVendor);

		categoryVendor = categoryVendorService.create(categoryVendor);
		LOGGER.debug("Created CategoryVendor with information: {}" , categoryVendor);

	    return categoryVendor;
	}

    @ApiOperation(value = "Returns the CategoryVendor instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CategoryVendor getCategoryVendor(@RequestParam("abIdry") Double abIdry, @RequestParam("abIdryve") String abIdryve, @RequestParam("abIdor") Double abIdor) {

        CategoryVendorId categoryvendorId = new CategoryVendorId();
        categoryvendorId.setAbIdry(abIdry);
        categoryvendorId.setAbIdryve(abIdryve);
        categoryvendorId.setAbIdor(abIdor);

        LOGGER.debug("Getting CategoryVendor with id: {}" , categoryvendorId);
        CategoryVendor categoryVendor = categoryVendorService.getById(categoryvendorId);
        LOGGER.debug("CategoryVendor details with id: {}" , categoryVendor);

        return categoryVendor;
    }



    @ApiOperation(value = "Updates the CategoryVendor instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CategoryVendor editCategoryVendor(@RequestParam("abIdry") Double abIdry, @RequestParam("abIdryve") String abIdryve, @RequestParam("abIdor") Double abIdor, @RequestBody CategoryVendor categoryVendor) {

        categoryVendor.setAbIdry(abIdry);
        categoryVendor.setAbIdryve(abIdryve);
        categoryVendor.setAbIdor(abIdor);

        LOGGER.debug("CategoryVendor details with id is updated with: {}" , categoryVendor);

        return categoryVendorService.update(categoryVendor);
    }

	@ApiOperation(value = "Partially updates the  CategoryVendor instance associated with the given composite-id.")
	@RequestMapping(value = "/composite-id", method = RequestMethod.PATCH)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public CategoryVendor patchCategoryVendor(@RequestParam("abIdry") Double abIdry, @RequestParam("abIdryve") String abIdryve, @RequestParam("abIdor") Double abIdor, @RequestBody @MapTo(CategoryVendor.class) Map<String, Object> categoryVendorPatch) {

        CategoryVendorId categoryvendorId = new CategoryVendorId();
        categoryvendorId.setAbIdry(abIdry);
        categoryvendorId.setAbIdryve(abIdryve);
        categoryvendorId.setAbIdor(abIdor);
        LOGGER.debug("Partially updating CategoryVendor with id: {}" , categoryvendorId);

        CategoryVendor categoryVendor = categoryVendorService.partialUpdate(categoryvendorId, categoryVendorPatch);
        LOGGER.debug("CategoryVendor details after partial update: {}" , categoryVendor);

        return categoryVendor;
    }


    @ApiOperation(value = "Deletes the CategoryVendor instance associated with the given composite-id.")
    @RequestMapping(value = "/composite-id", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteCategoryVendor(@RequestParam("abIdry") Double abIdry, @RequestParam("abIdryve") String abIdryve, @RequestParam("abIdor") Double abIdor) {

        CategoryVendorId categoryvendorId = new CategoryVendorId();
        categoryvendorId.setAbIdry(abIdry);
        categoryvendorId.setAbIdryve(abIdryve);
        categoryvendorId.setAbIdor(abIdor);

        LOGGER.debug("Deleting CategoryVendor with id: {}" , categoryvendorId);
        CategoryVendor categoryVendor = categoryVendorService.delete(categoryvendorId);

        return categoryVendor != null;
    }


    /**
     * @deprecated Use {@link #findCategoryVendors(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of CategoryVendor instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<CategoryVendor> searchCategoryVendorsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering CategoryVendors list by query filter:{}", (Object) queryFilters);
        return categoryVendorService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of CategoryVendor instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<CategoryVendor> findCategoryVendors(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering CategoryVendors list by filter:", query);
        return categoryVendorService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of CategoryVendor instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<CategoryVendor> filterCategoryVendors(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering CategoryVendors list by filter", query);
        return categoryVendorService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param.")
    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportCategoryVendors(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return categoryVendorService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportCategoryVendorsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = CategoryVendor.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> categoryVendorService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of CategoryVendor instances matching the optional query (q) request param.")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countCategoryVendors( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting CategoryVendors");
		return categoryVendorService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getCategoryVendorAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return categoryVendorService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service CategoryVendorService instance
	 */
	protected void setCategoryVendorService(CategoryVendorService service) {
		this.categoryVendorService = service;
	}

}