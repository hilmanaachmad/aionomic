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

import id.co.aio.procure_to_pay.sap_master.T024e;

/**
 * Service object for domain model class {@link T024e}.
 */
public interface T024eService {

    /**
     * Creates a new T024e. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on T024e if any.
     *
     * @param t024e Details of the T024e to be created; value cannot be null.
     * @return The newly created T024e.
     */
    T024e create(@Valid T024e t024e);


	/**
     * Returns T024e by given id if exists.
     *
     * @param t024eId The id of the T024e to get; value cannot be null.
     * @return T024e associated with the given t024eId.
	 * @throws EntityNotFoundException If no T024e is found.
     */
    T024e getById(String t024eId);

    /**
     * Find and return the T024e by given id if exists, returns null otherwise.
     *
     * @param t024eId The id of the T024e to get; value cannot be null.
     * @return T024e associated with the given t024eId.
     */
    T024e findById(String t024eId);

	/**
     * Find and return the list of T024es by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param t024eIds The id's of the T024e to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return T024es associated with the given t024eIds.
     */
    List<T024e> findByMultipleIds(List<String> t024eIds, boolean orderedReturn);


    /**
     * Updates the details of an existing T024e. It replaces all fields of the existing T024e with the given t024e.
     *
     * This method overrides the input field values using Server side or database managed properties defined on T024e if any.
     *
     * @param t024e The details of the T024e to be updated; value cannot be null.
     * @return The updated T024e.
     * @throws EntityNotFoundException if no T024e is found with given input.
     */
    T024e update(@Valid T024e t024e);


    /**
     * Partially updates the details of an existing T024e. It updates only the
     * fields of the existing T024e which are passed in the t024ePatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on T024e if any.
     *
     * @param t024eId The id of the T024e to be deleted; value cannot be null.
     * @param t024ePatch The partial data of T024e which is supposed to be updated; value cannot be null.
     * @return The updated T024e.
     * @throws EntityNotFoundException if no T024e is found with given input.
     */
    T024e partialUpdate(String t024eId, Map<String, Object> t024ePatch);

    /**
     * Deletes an existing T024e with the given id.
     *
     * @param t024eId The id of the T024e to be deleted; value cannot be null.
     * @return The deleted T024e.
     * @throws EntityNotFoundException if no T024e found with the given id.
     */
    T024e delete(String t024eId);

    /**
     * Deletes an existing T024e with the given object.
     *
     * @param t024e The instance of the T024e to be deleted; value cannot be null.
     */
    void delete(T024e t024e);

    /**
     * Find all T024es matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching T024es.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<T024e> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all T024es matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching T024es.
     *
     * @see Pageable
     * @see Page
     */
    Page<T024e> findAll(String query, Pageable pageable);

    /**
     * Exports all T024es matching the given input query to the given exportType format.
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
     * Exports all T024es matching the given input query to the given exportType format.
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
     * Retrieve the count of the T024es in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the T024e.
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