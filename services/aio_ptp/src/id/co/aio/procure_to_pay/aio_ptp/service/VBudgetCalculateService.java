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

import id.co.aio.procure_to_pay.aio_ptp.VBudgetCalculate;

/**
 * Service object for domain model class {@link VBudgetCalculate}.
 */
public interface VBudgetCalculateService {

    /**
     * Creates a new VBudgetCalculate. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VBudgetCalculate if any.
     *
     * @param vbudgetCalculate Details of the VBudgetCalculate to be created; value cannot be null.
     * @return The newly created VBudgetCalculate.
     */
    VBudgetCalculate create(@Valid VBudgetCalculate vbudgetCalculate);


	/**
     * Returns VBudgetCalculate by given id if exists.
     *
     * @param vbudgetcalculateId The id of the VBudgetCalculate to get; value cannot be null.
     * @return VBudgetCalculate associated with the given vbudgetcalculateId.
	 * @throws EntityNotFoundException If no VBudgetCalculate is found.
     */
    VBudgetCalculate getById(Integer vbudgetcalculateId);

    /**
     * Find and return the VBudgetCalculate by given id if exists, returns null otherwise.
     *
     * @param vbudgetcalculateId The id of the VBudgetCalculate to get; value cannot be null.
     * @return VBudgetCalculate associated with the given vbudgetcalculateId.
     */
    VBudgetCalculate findById(Integer vbudgetcalculateId);

	/**
     * Find and return the list of VBudgetCalculates by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vbudgetcalculateIds The id's of the VBudgetCalculate to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VBudgetCalculates associated with the given vbudgetcalculateIds.
     */
    List<VBudgetCalculate> findByMultipleIds(List<Integer> vbudgetcalculateIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VBudgetCalculate. It replaces all fields of the existing VBudgetCalculate with the given vbudgetCalculate.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VBudgetCalculate if any.
     *
     * @param vbudgetCalculate The details of the VBudgetCalculate to be updated; value cannot be null.
     * @return The updated VBudgetCalculate.
     * @throws EntityNotFoundException if no VBudgetCalculate is found with given input.
     */
    VBudgetCalculate update(@Valid VBudgetCalculate vbudgetCalculate);


    /**
     * Partially updates the details of an existing VBudgetCalculate. It updates only the
     * fields of the existing VBudgetCalculate which are passed in the vbudgetCalculatePatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VBudgetCalculate if any.
     *
     * @param vbudgetcalculateId The id of the VBudgetCalculate to be deleted; value cannot be null.
     * @param vbudgetCalculatePatch The partial data of VBudgetCalculate which is supposed to be updated; value cannot be null.
     * @return The updated VBudgetCalculate.
     * @throws EntityNotFoundException if no VBudgetCalculate is found with given input.
     */
    VBudgetCalculate partialUpdate(Integer vbudgetcalculateId, Map<String, Object> vbudgetCalculatePatch);

    /**
     * Deletes an existing VBudgetCalculate with the given id.
     *
     * @param vbudgetcalculateId The id of the VBudgetCalculate to be deleted; value cannot be null.
     * @return The deleted VBudgetCalculate.
     * @throws EntityNotFoundException if no VBudgetCalculate found with the given id.
     */
    VBudgetCalculate delete(Integer vbudgetcalculateId);

    /**
     * Deletes an existing VBudgetCalculate with the given object.
     *
     * @param vbudgetCalculate The instance of the VBudgetCalculate to be deleted; value cannot be null.
     */
    void delete(VBudgetCalculate vbudgetCalculate);

    /**
     * Find all VBudgetCalculates matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VBudgetCalculates.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VBudgetCalculate> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VBudgetCalculates matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VBudgetCalculates.
     *
     * @see Pageable
     * @see Page
     */
    Page<VBudgetCalculate> findAll(String query, Pageable pageable);

    /**
     * Exports all VBudgetCalculates matching the given input query to the given exportType format.
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
     * Exports all VBudgetCalculates matching the given input query to the given exportType format.
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
     * Retrieve the count of the VBudgetCalculates in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VBudgetCalculate.
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