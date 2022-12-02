/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

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

import id.co.aio.procure_to_pay.avm2.TlTrReak;
import id.co.aio.procure_to_pay.avm2.TlTrReakId;

/**
 * Service object for domain model class {@link TlTrReak}.
 */
public interface TlTrReakService {

    /**
     * Creates a new TlTrReak. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrReak if any.
     *
     * @param tlTrReak Details of the TlTrReak to be created; value cannot be null.
     * @return The newly created TlTrReak.
     */
    TlTrReak create(@Valid TlTrReak tlTrReak);


	/**
     * Returns TlTrReak by given id if exists.
     *
     * @param tltrreakId The id of the TlTrReak to get; value cannot be null.
     * @return TlTrReak associated with the given tltrreakId.
	 * @throws EntityNotFoundException If no TlTrReak is found.
     */
    TlTrReak getById(TlTrReakId tltrreakId);

    /**
     * Find and return the TlTrReak by given id if exists, returns null otherwise.
     *
     * @param tltrreakId The id of the TlTrReak to get; value cannot be null.
     * @return TlTrReak associated with the given tltrreakId.
     */
    TlTrReak findById(TlTrReakId tltrreakId);

	/**
     * Find and return the list of TlTrReaks by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tltrreakIds The id's of the TlTrReak to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TlTrReaks associated with the given tltrreakIds.
     */
    List<TlTrReak> findByMultipleIds(List<TlTrReakId> tltrreakIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TlTrReak. It replaces all fields of the existing TlTrReak with the given tlTrReak.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrReak if any.
     *
     * @param tlTrReak The details of the TlTrReak to be updated; value cannot be null.
     * @return The updated TlTrReak.
     * @throws EntityNotFoundException if no TlTrReak is found with given input.
     */
    TlTrReak update(@Valid TlTrReak tlTrReak);


    /**
     * Partially updates the details of an existing TlTrReak. It updates only the
     * fields of the existing TlTrReak which are passed in the tlTrReakPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrReak if any.
     *
     * @param tltrreakId The id of the TlTrReak to be deleted; value cannot be null.
     * @param tlTrReakPatch The partial data of TlTrReak which is supposed to be updated; value cannot be null.
     * @return The updated TlTrReak.
     * @throws EntityNotFoundException if no TlTrReak is found with given input.
     */
    TlTrReak partialUpdate(TlTrReakId tltrreakId, Map<String, Object> tlTrReakPatch);

    /**
     * Deletes an existing TlTrReak with the given id.
     *
     * @param tltrreakId The id of the TlTrReak to be deleted; value cannot be null.
     * @return The deleted TlTrReak.
     * @throws EntityNotFoundException if no TlTrReak found with the given id.
     */
    TlTrReak delete(TlTrReakId tltrreakId);

    /**
     * Deletes an existing TlTrReak with the given object.
     *
     * @param tlTrReak The instance of the TlTrReak to be deleted; value cannot be null.
     */
    void delete(TlTrReak tlTrReak);

    /**
     * Find all TlTrReaks matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlTrReaks.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TlTrReak> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TlTrReaks matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlTrReaks.
     *
     * @see Pageable
     * @see Page
     */
    Page<TlTrReak> findAll(String query, Pageable pageable);

    /**
     * Exports all TlTrReaks matching the given input query to the given exportType format.
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
     * Exports all TlTrReaks matching the given input query to the given exportType format.
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
     * Retrieve the count of the TlTrReaks in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TlTrReak.
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