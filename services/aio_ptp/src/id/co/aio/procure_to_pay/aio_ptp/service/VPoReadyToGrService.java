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

import id.co.aio.procure_to_pay.aio_ptp.VPoReadyToGr;

/**
 * Service object for domain model class {@link VPoReadyToGr}.
 */
public interface VPoReadyToGrService {

    /**
     * Creates a new VPoReadyToGr. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPoReadyToGr if any.
     *
     * @param vpoReadyToGr Details of the VPoReadyToGr to be created; value cannot be null.
     * @return The newly created VPoReadyToGr.
     */
    VPoReadyToGr create(@Valid VPoReadyToGr vpoReadyToGr);


	/**
     * Returns VPoReadyToGr by given id if exists.
     *
     * @param vporeadytogrId The id of the VPoReadyToGr to get; value cannot be null.
     * @return VPoReadyToGr associated with the given vporeadytogrId.
	 * @throws EntityNotFoundException If no VPoReadyToGr is found.
     */
    VPoReadyToGr getById(Integer vporeadytogrId);

    /**
     * Find and return the VPoReadyToGr by given id if exists, returns null otherwise.
     *
     * @param vporeadytogrId The id of the VPoReadyToGr to get; value cannot be null.
     * @return VPoReadyToGr associated with the given vporeadytogrId.
     */
    VPoReadyToGr findById(Integer vporeadytogrId);

	/**
     * Find and return the list of VPoReadyToGrs by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vporeadytogrIds The id's of the VPoReadyToGr to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VPoReadyToGrs associated with the given vporeadytogrIds.
     */
    List<VPoReadyToGr> findByMultipleIds(List<Integer> vporeadytogrIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VPoReadyToGr. It replaces all fields of the existing VPoReadyToGr with the given vpoReadyToGr.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPoReadyToGr if any.
     *
     * @param vpoReadyToGr The details of the VPoReadyToGr to be updated; value cannot be null.
     * @return The updated VPoReadyToGr.
     * @throws EntityNotFoundException if no VPoReadyToGr is found with given input.
     */
    VPoReadyToGr update(@Valid VPoReadyToGr vpoReadyToGr);


    /**
     * Partially updates the details of an existing VPoReadyToGr. It updates only the
     * fields of the existing VPoReadyToGr which are passed in the vpoReadyToGrPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VPoReadyToGr if any.
     *
     * @param vporeadytogrId The id of the VPoReadyToGr to be deleted; value cannot be null.
     * @param vpoReadyToGrPatch The partial data of VPoReadyToGr which is supposed to be updated; value cannot be null.
     * @return The updated VPoReadyToGr.
     * @throws EntityNotFoundException if no VPoReadyToGr is found with given input.
     */
    VPoReadyToGr partialUpdate(Integer vporeadytogrId, Map<String, Object> vpoReadyToGrPatch);

    /**
     * Deletes an existing VPoReadyToGr with the given id.
     *
     * @param vporeadytogrId The id of the VPoReadyToGr to be deleted; value cannot be null.
     * @return The deleted VPoReadyToGr.
     * @throws EntityNotFoundException if no VPoReadyToGr found with the given id.
     */
    VPoReadyToGr delete(Integer vporeadytogrId);

    /**
     * Deletes an existing VPoReadyToGr with the given object.
     *
     * @param vpoReadyToGr The instance of the VPoReadyToGr to be deleted; value cannot be null.
     */
    void delete(VPoReadyToGr vpoReadyToGr);

    /**
     * Find all VPoReadyToGrs matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPoReadyToGrs.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VPoReadyToGr> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VPoReadyToGrs matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VPoReadyToGrs.
     *
     * @see Pageable
     * @see Page
     */
    Page<VPoReadyToGr> findAll(String query, Pageable pageable);

    /**
     * Exports all VPoReadyToGrs matching the given input query to the given exportType format.
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
     * Exports all VPoReadyToGrs matching the given input query to the given exportType format.
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
     * Retrieve the count of the VPoReadyToGrs in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VPoReadyToGr.
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