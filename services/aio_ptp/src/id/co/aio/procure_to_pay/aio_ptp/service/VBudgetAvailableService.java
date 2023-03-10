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

import id.co.aio.procure_to_pay.aio_ptp.VBudgetAvailable;

/**
 * Service object for domain model class {@link VBudgetAvailable}.
 */
public interface VBudgetAvailableService {

    /**
     * Creates a new VBudgetAvailable. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VBudgetAvailable if any.
     *
     * @param vbudgetAvailable Details of the VBudgetAvailable to be created; value cannot be null.
     * @return The newly created VBudgetAvailable.
     */
    VBudgetAvailable create(@Valid VBudgetAvailable vbudgetAvailable);


	/**
     * Returns VBudgetAvailable by given id if exists.
     *
     * @param vbudgetavailableId The id of the VBudgetAvailable to get; value cannot be null.
     * @return VBudgetAvailable associated with the given vbudgetavailableId.
	 * @throws EntityNotFoundException If no VBudgetAvailable is found.
     */
    VBudgetAvailable getById(Integer vbudgetavailableId);

    /**
     * Find and return the VBudgetAvailable by given id if exists, returns null otherwise.
     *
     * @param vbudgetavailableId The id of the VBudgetAvailable to get; value cannot be null.
     * @return VBudgetAvailable associated with the given vbudgetavailableId.
     */
    VBudgetAvailable findById(Integer vbudgetavailableId);

	/**
     * Find and return the list of VBudgetAvailables by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vbudgetavailableIds The id's of the VBudgetAvailable to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VBudgetAvailables associated with the given vbudgetavailableIds.
     */
    List<VBudgetAvailable> findByMultipleIds(List<Integer> vbudgetavailableIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VBudgetAvailable. It replaces all fields of the existing VBudgetAvailable with the given vbudgetAvailable.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VBudgetAvailable if any.
     *
     * @param vbudgetAvailable The details of the VBudgetAvailable to be updated; value cannot be null.
     * @return The updated VBudgetAvailable.
     * @throws EntityNotFoundException if no VBudgetAvailable is found with given input.
     */
    VBudgetAvailable update(@Valid VBudgetAvailable vbudgetAvailable);


    /**
     * Partially updates the details of an existing VBudgetAvailable. It updates only the
     * fields of the existing VBudgetAvailable which are passed in the vbudgetAvailablePatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VBudgetAvailable if any.
     *
     * @param vbudgetavailableId The id of the VBudgetAvailable to be deleted; value cannot be null.
     * @param vbudgetAvailablePatch The partial data of VBudgetAvailable which is supposed to be updated; value cannot be null.
     * @return The updated VBudgetAvailable.
     * @throws EntityNotFoundException if no VBudgetAvailable is found with given input.
     */
    VBudgetAvailable partialUpdate(Integer vbudgetavailableId, Map<String, Object> vbudgetAvailablePatch);

    /**
     * Deletes an existing VBudgetAvailable with the given id.
     *
     * @param vbudgetavailableId The id of the VBudgetAvailable to be deleted; value cannot be null.
     * @return The deleted VBudgetAvailable.
     * @throws EntityNotFoundException if no VBudgetAvailable found with the given id.
     */
    VBudgetAvailable delete(Integer vbudgetavailableId);

    /**
     * Deletes an existing VBudgetAvailable with the given object.
     *
     * @param vbudgetAvailable The instance of the VBudgetAvailable to be deleted; value cannot be null.
     */
    void delete(VBudgetAvailable vbudgetAvailable);

    /**
     * Find all VBudgetAvailables matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VBudgetAvailables.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VBudgetAvailable> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VBudgetAvailables matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VBudgetAvailables.
     *
     * @see Pageable
     * @see Page
     */
    Page<VBudgetAvailable> findAll(String query, Pageable pageable);

    /**
     * Exports all VBudgetAvailables matching the given input query to the given exportType format.
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
     * Exports all VBudgetAvailables matching the given input query to the given exportType format.
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
     * Retrieve the count of the VBudgetAvailables in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VBudgetAvailable.
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