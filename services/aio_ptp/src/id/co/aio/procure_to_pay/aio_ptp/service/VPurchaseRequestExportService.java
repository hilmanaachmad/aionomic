/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.VPurchaseRequestExport;

/**
 * Service object for domain model class {@link VPurchaseRequestExport}.
 */
public interface VPurchaseRequestExportService {

    /**
     * Creates a new VPurchaseRequestExport. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPurchaseRequestExport if any.
     *
     * @param vpurchaseRequestExport Details of the VPurchaseRequestExport to be created; value cannot be null.
     * @return The newly created VPurchaseRequestExport.
     */
    VPurchaseRequestExport create(@Valid VPurchaseRequestExport vpurchaseRequestExport);


	/**
     * Returns VPurchaseRequestExport by given id if exists.
     *
     * @param vpurchaserequestexportId The id of the VPurchaseRequestExport to get; value cannot be null.
     * @return VPurchaseRequestExport associated with the given vpurchaserequestexportId.
	 * @throws EntityNotFoundException If no VPurchaseRequestExport is found.
     */
    VPurchaseRequestExport getById(Integer vpurchaserequestexportId);

    /**
     * Find and return the VPurchaseRequestExport by given id if exists, returns null otherwise.
     *
     * @param vpurchaserequestexportId The id of the VPurchaseRequestExport to get; value cannot be null.
     * @return VPurchaseRequestExport associated with the given vpurchaserequestexportId.
     */
    VPurchaseRequestExport findById(Integer vpurchaserequestexportId);

	/**
     * Find and return the list of VPurchaseRequestExports by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vpurchaserequestexportIds The id's of the VPurchaseRequestExport to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VPurchaseRequestExports associated with the given vpurchaserequestexportIds.
     */
    List<VPurchaseRequestExport> findByMultipleIds(List<Integer> vpurchaserequestexportIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VPurchaseRequestExport. It replaces all fields of the existing VPurchaseRequestExport with the given vpurchaseRequestExport.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPurchaseRequestExport if any.
     *
     * @param vpurchaseRequestExport The details of the VPurchaseRequestExport to be updated; value cannot be null.
     * @return The updated VPurchaseRequestExport.
     * @throws EntityNotFoundException if no VPurchaseRequestExport is found with given input.
     */
    VPurchaseRequestExport update(@Valid VPurchaseRequestExport vpurchaseRequestExport);


    /**
     * Partially updates the details of an existing VPurchaseRequestExport. It updates only the
     * fields of the existing VPurchaseRequestExport which are passed in the vpurchaseRequestExportPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPurchaseRequestExport if any.
     *
     * @param vpurchaserequestexportId The id of the VPurchaseRequestExport to be deleted; value cannot be null.
     * @param vpurchaseRequestExportPatch The partial data of VPurchaseRequestExport which is supposed to be updated; value cannot be null.
     * @return The updated VPurchaseRequestExport.
     * @throws EntityNotFoundException if no VPurchaseRequestExport is found with given input.
     */
    VPurchaseRequestExport partialUpdate(Integer vpurchaserequestexportId, Map<String, Object> vpurchaseRequestExportPatch);

    /**
     * Deletes an existing VPurchaseRequestExport with the given id.
     *
     * @param vpurchaserequestexportId The id of the VPurchaseRequestExport to be deleted; value cannot be null.
     * @return The deleted VPurchaseRequestExport.
     * @throws EntityNotFoundException if no VPurchaseRequestExport found with the given id.
     */
    VPurchaseRequestExport delete(Integer vpurchaserequestexportId);

    /**
     * Deletes an existing VPurchaseRequestExport with the given object.
     *
     * @param vpurchaseRequestExport The instance of the VPurchaseRequestExport to be deleted; value cannot be null.
     */
    void delete(VPurchaseRequestExport vpurchaseRequestExport);

    /**
     * Find all VPurchaseRequestExports matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPurchaseRequestExports.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VPurchaseRequestExport> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VPurchaseRequestExports matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPurchaseRequestExports.
     *
     * @see Pageable
     * @see Page
     */
    Page<VPurchaseRequestExport> findAll(String query, Pageable pageable);

    /**
     * Exports all VPurchaseRequestExports matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all VPurchaseRequestExports matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the VPurchaseRequestExports in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VPurchaseRequestExport.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}