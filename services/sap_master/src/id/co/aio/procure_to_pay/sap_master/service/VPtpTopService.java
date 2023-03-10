/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.sap_master.service;

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

import id.co.aio.procure_to_pay.sap_master.VPtpTop;
import id.co.aio.procure_to_pay.sap_master.VPtpTopId;

/**
 * Service object for domain model class {@link VPtpTop}.
 */
public interface VPtpTopService {

    /**
     * Creates a new VPtpTop. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPtpTop if any.
     *
     * @param vptpTop Details of the VPtpTop to be created; value cannot be null.
     * @return The newly created VPtpTop.
     */
    VPtpTop create(@Valid VPtpTop vptpTop);


	/**
     * Returns VPtpTop by given id if exists.
     *
     * @param vptptopId The id of the VPtpTop to get; value cannot be null.
     * @return VPtpTop associated with the given vptptopId.
	 * @throws EntityNotFoundException If no VPtpTop is found.
     */
    VPtpTop getById(VPtpTopId vptptopId);

    /**
     * Find and return the VPtpTop by given id if exists, returns null otherwise.
     *
     * @param vptptopId The id of the VPtpTop to get; value cannot be null.
     * @return VPtpTop associated with the given vptptopId.
     */
    VPtpTop findById(VPtpTopId vptptopId);

	/**
     * Find and return the list of VPtpTops by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vptptopIds The id's of the VPtpTop to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VPtpTops associated with the given vptptopIds.
     */
    List<VPtpTop> findByMultipleIds(List<VPtpTopId> vptptopIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VPtpTop. It replaces all fields of the existing VPtpTop with the given vptpTop.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPtpTop if any.
     *
     * @param vptpTop The details of the VPtpTop to be updated; value cannot be null.
     * @return The updated VPtpTop.
     * @throws EntityNotFoundException if no VPtpTop is found with given input.
     */
    VPtpTop update(@Valid VPtpTop vptpTop);


    /**
     * Partially updates the details of an existing VPtpTop. It updates only the
     * fields of the existing VPtpTop which are passed in the vptpTopPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPtpTop if any.
     *
     * @param vptptopId The id of the VPtpTop to be deleted; value cannot be null.
     * @param vptpTopPatch The partial data of VPtpTop which is supposed to be updated; value cannot be null.
     * @return The updated VPtpTop.
     * @throws EntityNotFoundException if no VPtpTop is found with given input.
     */
    VPtpTop partialUpdate(VPtpTopId vptptopId, Map<String, Object> vptpTopPatch);

    /**
     * Deletes an existing VPtpTop with the given id.
     *
     * @param vptptopId The id of the VPtpTop to be deleted; value cannot be null.
     * @return The deleted VPtpTop.
     * @throws EntityNotFoundException if no VPtpTop found with the given id.
     */
    VPtpTop delete(VPtpTopId vptptopId);

    /**
     * Deletes an existing VPtpTop with the given object.
     *
     * @param vptpTop The instance of the VPtpTop to be deleted; value cannot be null.
     */
    void delete(VPtpTop vptpTop);

    /**
     * Find all VPtpTops matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPtpTops.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VPtpTop> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VPtpTops matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPtpTops.
     *
     * @see Pageable
     * @see Page
     */
    Page<VPtpTop> findAll(String query, Pageable pageable);

    /**
     * Exports all VPtpTops matching the given input query to the given exportType format.
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
     * Exports all VPtpTops matching the given input query to the given exportType format.
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
     * Retrieve the count of the VPtpTops in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VPtpTop.
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