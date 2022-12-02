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

import id.co.aio.procure_to_pay.avm2.TlMsTyon;

/**
 * Service object for domain model class {@link TlMsTyon}.
 */
public interface TlMsTyonService {

    /**
     * Creates a new TlMsTyon. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsTyon if any.
     *
     * @param tlMsTyon Details of the TlMsTyon to be created; value cannot be null.
     * @return The newly created TlMsTyon.
     */
    TlMsTyon create(@Valid TlMsTyon tlMsTyon);


	/**
     * Returns TlMsTyon by given id if exists.
     *
     * @param tlmstyonId The id of the TlMsTyon to get; value cannot be null.
     * @return TlMsTyon associated with the given tlmstyonId.
	 * @throws EntityNotFoundException If no TlMsTyon is found.
     */
    TlMsTyon getById(Integer tlmstyonId);

    /**
     * Find and return the TlMsTyon by given id if exists, returns null otherwise.
     *
     * @param tlmstyonId The id of the TlMsTyon to get; value cannot be null.
     * @return TlMsTyon associated with the given tlmstyonId.
     */
    TlMsTyon findById(Integer tlmstyonId);

	/**
     * Find and return the list of TlMsTyons by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tlmstyonIds The id's of the TlMsTyon to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TlMsTyons associated with the given tlmstyonIds.
     */
    List<TlMsTyon> findByMultipleIds(List<Integer> tlmstyonIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TlMsTyon. It replaces all fields of the existing TlMsTyon with the given tlMsTyon.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsTyon if any.
     *
     * @param tlMsTyon The details of the TlMsTyon to be updated; value cannot be null.
     * @return The updated TlMsTyon.
     * @throws EntityNotFoundException if no TlMsTyon is found with given input.
     */
    TlMsTyon update(@Valid TlMsTyon tlMsTyon);


    /**
     * Partially updates the details of an existing TlMsTyon. It updates only the
     * fields of the existing TlMsTyon which are passed in the tlMsTyonPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TlMsTyon if any.
     *
     * @param tlmstyonId The id of the TlMsTyon to be deleted; value cannot be null.
     * @param tlMsTyonPatch The partial data of TlMsTyon which is supposed to be updated; value cannot be null.
     * @return The updated TlMsTyon.
     * @throws EntityNotFoundException if no TlMsTyon is found with given input.
     */
    TlMsTyon partialUpdate(Integer tlmstyonId, Map<String, Object> tlMsTyonPatch);

    /**
     * Deletes an existing TlMsTyon with the given id.
     *
     * @param tlmstyonId The id of the TlMsTyon to be deleted; value cannot be null.
     * @return The deleted TlMsTyon.
     * @throws EntityNotFoundException if no TlMsTyon found with the given id.
     */
    TlMsTyon delete(Integer tlmstyonId);

    /**
     * Deletes an existing TlMsTyon with the given object.
     *
     * @param tlMsTyon The instance of the TlMsTyon to be deleted; value cannot be null.
     */
    void delete(TlMsTyon tlMsTyon);

    /**
     * Find all TlMsTyons matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlMsTyons.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TlMsTyon> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TlMsTyons matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TlMsTyons.
     *
     * @see Pageable
     * @see Page
     */
    Page<TlMsTyon> findAll(String query, Pageable pageable);

    /**
     * Exports all TlMsTyons matching the given input query to the given exportType format.
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
     * Exports all TlMsTyons matching the given input query to the given exportType format.
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
     * Retrieve the count of the TlMsTyons in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TlMsTyon.
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