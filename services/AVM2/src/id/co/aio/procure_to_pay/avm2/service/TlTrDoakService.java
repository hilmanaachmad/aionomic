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

import id.co.aio.procure_to_pay.avm2.TlTrDoak;

/**
 * Service object for domain model class {@link TlTrDoak}.
 */
public interface TlTrDoakService {

    /**
     * Creates a new TlTrDoak. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrDoak if any.
     *
     * @param tlTrDoak Details of the TlTrDoak to be created; value cannot be null.
     * @return The newly created TlTrDoak.
     */
    TlTrDoak create(@Valid TlTrDoak tlTrDoak);


	/**
     * Returns TlTrDoak by given id if exists.
     *
     * @param tltrdoakId The id of the TlTrDoak to get; value cannot be null.
     * @return TlTrDoak associated with the given tltrdoakId.
	 * @throws EntityNotFoundException If no TlTrDoak is found.
     */
    TlTrDoak getById(Integer tltrdoakId);

    /**
     * Find and return the TlTrDoak by given id if exists, returns null otherwise.
     *
     * @param tltrdoakId The id of the TlTrDoak to get; value cannot be null.
     * @return TlTrDoak associated with the given tltrdoakId.
     */
    TlTrDoak findById(Integer tltrdoakId);

	/**
     * Find and return the list of TlTrDoaks by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tltrdoakIds The id's of the TlTrDoak to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TlTrDoaks associated with the given tltrdoakIds.
     */
    List<TlTrDoak> findByMultipleIds(List<Integer> tltrdoakIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TlTrDoak. It replaces all fields of the existing TlTrDoak with the given tlTrDoak.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrDoak if any.
     *
     * @param tlTrDoak The details of the TlTrDoak to be updated; value cannot be null.
     * @return The updated TlTrDoak.
     * @throws EntityNotFoundException if no TlTrDoak is found with given input.
     */
    TlTrDoak update(@Valid TlTrDoak tlTrDoak);


    /**
     * Partially updates the details of an existing TlTrDoak. It updates only the
     * fields of the existing TlTrDoak which are passed in the tlTrDoakPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrDoak if any.
     *
     * @param tltrdoakId The id of the TlTrDoak to be deleted; value cannot be null.
     * @param tlTrDoakPatch The partial data of TlTrDoak which is supposed to be updated; value cannot be null.
     * @return The updated TlTrDoak.
     * @throws EntityNotFoundException if no TlTrDoak is found with given input.
     */
    TlTrDoak partialUpdate(Integer tltrdoakId, Map<String, Object> tlTrDoakPatch);

    /**
     * Deletes an existing TlTrDoak with the given id.
     *
     * @param tltrdoakId The id of the TlTrDoak to be deleted; value cannot be null.
     * @return The deleted TlTrDoak.
     * @throws EntityNotFoundException if no TlTrDoak found with the given id.
     */
    TlTrDoak delete(Integer tltrdoakId);

    /**
     * Deletes an existing TlTrDoak with the given object.
     *
     * @param tlTrDoak The instance of the TlTrDoak to be deleted; value cannot be null.
     */
    void delete(TlTrDoak tlTrDoak);

    /**
     * Find all TlTrDoaks matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlTrDoaks.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TlTrDoak> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TlTrDoaks matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlTrDoaks.
     *
     * @see Pageable
     * @see Page
     */
    Page<TlTrDoak> findAll(String query, Pageable pageable);

    /**
     * Exports all TlTrDoaks matching the given input query to the given exportType format.
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
     * Exports all TlTrDoaks matching the given input query to the given exportType format.
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
     * Retrieve the count of the TlTrDoaks in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TlTrDoak.
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