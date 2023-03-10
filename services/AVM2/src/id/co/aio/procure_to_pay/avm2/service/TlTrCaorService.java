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

import id.co.aio.procure_to_pay.avm2.TlTrCaor;

/**
 * Service object for domain model class {@link TlTrCaor}.
 */
public interface TlTrCaorService {

    /**
     * Creates a new TlTrCaor. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrCaor if any.
     *
     * @param tlTrCaor Details of the TlTrCaor to be created; value cannot be null.
     * @return The newly created TlTrCaor.
     */
    TlTrCaor create(@Valid TlTrCaor tlTrCaor);


	/**
     * Returns TlTrCaor by given id if exists.
     *
     * @param tltrcaorId The id of the TlTrCaor to get; value cannot be null.
     * @return TlTrCaor associated with the given tltrcaorId.
	 * @throws EntityNotFoundException If no TlTrCaor is found.
     */
    TlTrCaor getById(Integer tltrcaorId);

    /**
     * Find and return the TlTrCaor by given id if exists, returns null otherwise.
     *
     * @param tltrcaorId The id of the TlTrCaor to get; value cannot be null.
     * @return TlTrCaor associated with the given tltrcaorId.
     */
    TlTrCaor findById(Integer tltrcaorId);

	/**
     * Find and return the list of TlTrCaors by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tltrcaorIds The id's of the TlTrCaor to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TlTrCaors associated with the given tltrcaorIds.
     */
    List<TlTrCaor> findByMultipleIds(List<Integer> tltrcaorIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TlTrCaor. It replaces all fields of the existing TlTrCaor with the given tlTrCaor.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrCaor if any.
     *
     * @param tlTrCaor The details of the TlTrCaor to be updated; value cannot be null.
     * @return The updated TlTrCaor.
     * @throws EntityNotFoundException if no TlTrCaor is found with given input.
     */
    TlTrCaor update(@Valid TlTrCaor tlTrCaor);


    /**
     * Partially updates the details of an existing TlTrCaor. It updates only the
     * fields of the existing TlTrCaor which are passed in the tlTrCaorPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlTrCaor if any.
     *
     * @param tltrcaorId The id of the TlTrCaor to be deleted; value cannot be null.
     * @param tlTrCaorPatch The partial data of TlTrCaor which is supposed to be updated; value cannot be null.
     * @return The updated TlTrCaor.
     * @throws EntityNotFoundException if no TlTrCaor is found with given input.
     */
    TlTrCaor partialUpdate(Integer tltrcaorId, Map<String, Object> tlTrCaorPatch);

    /**
     * Deletes an existing TlTrCaor with the given id.
     *
     * @param tltrcaorId The id of the TlTrCaor to be deleted; value cannot be null.
     * @return The deleted TlTrCaor.
     * @throws EntityNotFoundException if no TlTrCaor found with the given id.
     */
    TlTrCaor delete(Integer tltrcaorId);

    /**
     * Deletes an existing TlTrCaor with the given object.
     *
     * @param tlTrCaor The instance of the TlTrCaor to be deleted; value cannot be null.
     */
    void delete(TlTrCaor tlTrCaor);

    /**
     * Find all TlTrCaors matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlTrCaors.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TlTrCaor> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TlTrCaors matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlTrCaors.
     *
     * @see Pageable
     * @see Page
     */
    Page<TlTrCaor> findAll(String query, Pageable pageable);

    /**
     * Exports all TlTrCaors matching the given input query to the given exportType format.
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
     * Exports all TlTrCaors matching the given input query to the given exportType format.
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
     * Retrieve the count of the TlTrCaors in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TlTrCaor.
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