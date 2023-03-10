/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_employee.service;

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

import id.co.aio.procure_to_pay.aio_employee.EmployeeData;

/**
 * Service object for domain model class {@link EmployeeData}.
 */
public interface EmployeeDataService {

    /**
     * Creates a new EmployeeData. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on EmployeeData if any.
     *
     * @param employeeData Details of the EmployeeData to be created; value cannot be null.
     * @return The newly created EmployeeData.
     */
    EmployeeData create(@Valid EmployeeData employeeData);


	/**
     * Returns EmployeeData by given id if exists.
     *
     * @param employeedataId The id of the EmployeeData to get; value cannot be null.
     * @return EmployeeData associated with the given employeedataId.
	 * @throws EntityNotFoundException If no EmployeeData is found.
     */
    EmployeeData getById(Integer employeedataId);

    /**
     * Find and return the EmployeeData by given id if exists, returns null otherwise.
     *
     * @param employeedataId The id of the EmployeeData to get; value cannot be null.
     * @return EmployeeData associated with the given employeedataId.
     */
    EmployeeData findById(Integer employeedataId);

	/**
     * Find and return the list of EmployeeDatas by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param employeedataIds The id's of the EmployeeData to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return EmployeeDatas associated with the given employeedataIds.
     */
    List<EmployeeData> findByMultipleIds(List<Integer> employeedataIds, boolean orderedReturn);


    /**
     * Updates the details of an existing EmployeeData. It replaces all fields of the existing EmployeeData with the given employeeData.
     *
     * This method overrides the input field values using Server side or database managed properties defined on EmployeeData if any.
     *
     * @param employeeData The details of the EmployeeData to be updated; value cannot be null.
     * @return The updated EmployeeData.
     * @throws EntityNotFoundException if no EmployeeData is found with given input.
     */
    EmployeeData update(@Valid EmployeeData employeeData);


    /**
     * Partially updates the details of an existing EmployeeData. It updates only the
     * fields of the existing EmployeeData which are passed in the employeeDataPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on EmployeeData if any.
     *
     * @param employeedataId The id of the EmployeeData to be deleted; value cannot be null.
     * @param employeeDataPatch The partial data of EmployeeData which is supposed to be updated; value cannot be null.
     * @return The updated EmployeeData.
     * @throws EntityNotFoundException if no EmployeeData is found with given input.
     */
    EmployeeData partialUpdate(Integer employeedataId, Map<String, Object> employeeDataPatch);

    /**
     * Deletes an existing EmployeeData with the given id.
     *
     * @param employeedataId The id of the EmployeeData to be deleted; value cannot be null.
     * @return The deleted EmployeeData.
     * @throws EntityNotFoundException if no EmployeeData found with the given id.
     */
    EmployeeData delete(Integer employeedataId);

    /**
     * Deletes an existing EmployeeData with the given object.
     *
     * @param employeeData The instance of the EmployeeData to be deleted; value cannot be null.
     */
    void delete(EmployeeData employeeData);

    /**
     * Find all EmployeeDatas matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching EmployeeDatas.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<EmployeeData> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all EmployeeDatas matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching EmployeeDatas.
     *
     * @see Pageable
     * @see Page
     */
    Page<EmployeeData> findAll(String query, Pageable pageable);

    /**
     * Exports all EmployeeDatas matching the given input query to the given exportType format.
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
     * Exports all EmployeeDatas matching the given input query to the given exportType format.
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
     * Retrieve the count of the EmployeeDatas in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the EmployeeData.
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